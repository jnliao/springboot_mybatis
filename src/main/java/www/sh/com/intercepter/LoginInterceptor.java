package www.sh.com.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import www.sh.com.common.CommonConstant;
import www.sh.com.util.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Administrator
 * 登录拦截器，检查cookie信息
 */

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        if(StringUtils.isBlank(accessToken)){
            unauthorized(request,response);
            return false;
        }

        String userInfo = null;
        try {
            userInfo = redisService.get(CommonConstant.UserConstant.REDIS_USER_LOGIN + accessToken);
        } catch (Exception e) {
            log.error("LoginInterceptor preHandle has an error: " + e);
        }

        if(StringUtils.isNotBlank(userInfo)){
            log.info("登录成功！");
            return true;
        }

        unauthorized(request,response);
        return false;

    }

    private void unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(401);
        response.getWriter().println("Unauthorized");
        response.getWriter().close();
        log.info("Authorization invaid: {}", request.getHeader("Authorization"));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
