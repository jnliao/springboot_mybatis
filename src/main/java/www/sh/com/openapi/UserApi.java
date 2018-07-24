package www.sh.com.openapi;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import www.sh.com.common.ResultForWeb;
import www.sh.com.service.IUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liaojinneng
 * @date 2018/7/20
 */
@RestController
@RequestMapping("/bigClass/outInterface")
@Api(value = "user", description = "用户模块对外接口")
public class UserApi {

    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "根据id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value="鉴权中心分配的账号",dataType="String",paramType="header",required = true),
            @ApiImplicitParam(name = "timestamp",value="请求时间戳",dataType="Long",paramType="header",required = true),
            @ApiImplicitParam(name = "signature",value="根据MD5加密（账号+秘钥+时间戳）求出的签名",dataType="String",paramType="header",required = true)
    })
    @RequestMapping(value = "/user/findById", method = RequestMethod.POST)
    public ResultForWeb getCrmUsers(HttpServletRequest request,
                                    @ApiParam(name = "id",value="用户id", required = true) @RequestParam("id") Long id) {
        return iUserService.findById(id);
    }

}
