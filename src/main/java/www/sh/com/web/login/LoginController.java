package www.sh.com.web.login;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import www.sh.com.common.ResultCodeEnum;
import www.sh.com.common.ResultForWeb;
import www.sh.com.service.ILoginservice;
import www.sh.com.util.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 * 登陆、退出
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ILoginservice iLoginservice;


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultForWeb login(
            @ApiParam(name = "account",value="账号", required = true) @RequestParam("account") String account,
            @ApiParam(name = "password",value="密码", required = true) @RequestParam("password") String password){
        return iLoginservice.login(account,password);
    }

    @ApiOperation(value = "退出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",value="登录凭证",dataType="String",paramType="header",required = true),
    })
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultForWeb logout(HttpServletRequest request, HttpServletResponse response){
        try {
            if (StringUtils.isEmpty(request.getHeader("Authorization"))){
                return ResultForWeb.success("注销成功");
            }
            return iLoginservice.logout(request.getHeader("Authorization"));
        } catch (Exception e) {
            log.error("LoginController login has error: ", e);
        }
        return ResultForWeb.errorCommon();
    }


}
