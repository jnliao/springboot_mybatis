package www.sh.com.web.login;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.sh.com.common.ResultForWeb;
import www.sh.com.service.ILoginservice;

/**
 *
 * @author Administrator
 * 登陆、退出
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ILoginservice iLoginservice;


    @ApiOperation(value = "登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultForWeb login(String account, String password){
        return iLoginservice.login(account,password);
    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultForWeb logout(String account){
        return iLoginservice.logout(account);
    }


}
