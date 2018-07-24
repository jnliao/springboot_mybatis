package www.sh.com.intercepter;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import www.sh.com.mapper.OpenApiConfigMapper;
import www.sh.com.pojo.domain.OpenApiConfig;
import www.sh.com.util.Md5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 对外接口拦截器
 * @author Administrator
 */
@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {
    /**
     * 时间戳过期时间
     * 5 min
     */
    private static final Integer EXPIRATION_TIME = 5 * 60 * 1000;

    @Autowired
    private OpenApiConfigMapper openApiConfigMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String requestURI = request.getRequestURI();
        String account = request.getHeader("account");
        Long timeStamp = Long.parseLong(request.getHeader("timestamp"));
        String signature = request.getHeader("signature");

        //时间戳过期
        if((System.currentTimeMillis() - timeStamp) > EXPIRATION_TIME || (timeStamp - System.currentTimeMillis()) > EXPIRATION_TIME){
            String msg = "The timestamp expired!";
            requestFailure(request,response,msg);
            return false;
        }

        OpenApiConfig condition = new OpenApiConfig();
        condition.setAccount(account);
        condition.setDeleted(false);
        EntityWrapper<OpenApiConfig> entityWrapper = new EntityWrapper<>(condition);
        List<OpenApiConfig> openApiAccountConfigs = openApiConfigMapper.selectList(entityWrapper);

        //账号不存在
        if(openApiAccountConfigs==null || openApiAccountConfigs.size()<=0){
            String msg = "Account does not exist!";
            requestFailure(request,response,msg);
            return false;
        }

        OpenApiConfig openapiAccountConfig = openApiAccountConfigs.get(0);
        String apiKey = openapiAccountConfig.getApiKey();
        String param = account+apiKey+timeStamp;
        String md5Sign = Md5Util.md5LowerCase(param);
        List<String> apiUrlList = Arrays.asList(openapiAccountConfig.getApiUrls().split(","));

        //签名错误
        if(!signature.equals(md5Sign)){
            String msg = "Signature error!";
            requestFailure(request,response,msg);
            return false;
        }

        //接口不在允许访问的接口列表中
        if(!apiUrlList.contains(requestURI)){
            String msg = "This interface is not in the list of interfaces that are allowed to access! This interface is:"+requestURI;
            requestFailure(request,response,msg);
            return false;
        }

        return true;
    }

    private void requestFailure(HttpServletRequest request, HttpServletResponse response,String msg) throws IOException {
        response.setStatus(401);
        response.getWriter().println("Request Failure! Fail reason: " + msg);
        response.getWriter().close();
    }


	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
