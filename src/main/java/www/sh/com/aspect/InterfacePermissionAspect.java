package www.sh.com.aspect;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.sh.com.annotation.PermissionInitExtend;
import www.sh.com.annotation.PermissionInitExtendList;
import www.sh.com.common.CommonConstant;
import www.sh.com.common.ResultCodeEnum;
import www.sh.com.common.ResultForWeb;
import www.sh.com.pojo.vo.UserVo;
import www.sh.com.util.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 切面处理类—接口权限
 * @author liaojinneng
 */
@Aspect
@Component
@Slf4j
public class InterfacePermissionAspect {

	@Autowired
	private RedisService redisService;

	@Pointcut("@annotation(www.sh.com.annotation.PermissionInitExtendList)")
	public void logPointCut() {}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		String userStr = null;
		PermissionInitExtendList extendList = null;
		for (Method method : point.getTarget().getClass().getMethods()){
			if (method.getName().equals(point.getSignature().getName())) {
				extendList = method.getAnnotation(PermissionInitExtendList.class);
			}
		}

		if(extendList == null || extendList.value().length <= 0){
			return ResultForWeb.error(ResultCodeEnum.AUTH_FAIL);
		}

		for (Object object : args){
			if (object instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) object;
				String accessToken = request.getHeader("Authorization");
				userStr = redisService.get(CommonConstant.UserConstant.REDIS_USER_LOGIN + accessToken);
			}
		}

		if(StringUtils.isBlank(userStr)){
			return ResultForWeb.error(ResultCodeEnum.AUTH_FAIL);
		}

		UserVo userVo = JSON.parseObject(userStr, UserVo.class);
		if(userVo == null){
			return ResultForWeb.error(ResultCodeEnum.AUTH_FAIL);
		}

		List<String> permissionCodes = userVo.getPermissionCodes();
		if(permissionCodes==null || permissionCodes.size()<=0){
			return ResultForWeb.error(ResultCodeEnum.AUTH_FAIL);
		}

		int count = 0;
		for (PermissionInitExtend permissionInitExtend : extendList.value()) {
			String code = permissionInitExtend.code();
			for (String str : permissionCodes) {
				if (str.equals(code)) {
					count++;
					break;
				}
			}
		}

		if (count == extendList.value().length) {
			return point.proceed();
		}

		return ResultForWeb.error(ResultCodeEnum.AUTH_FAIL);
	}
}
