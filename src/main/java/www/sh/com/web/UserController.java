package www.sh.com.web;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import www.sh.com.pojo.vo.UserVo;
import www.sh.com.service.IUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
@RestController
@RequestMapping("/user")
@Api(value = "user", description = "用户")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "根据id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",value="登录凭证",dataType="String",paramType="header",required = true),
    })
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public UserVo getCrmUsers(HttpServletRequest request,
                              @ApiParam(name = "id",value="用户id", required = true) @RequestParam("id") Long id) {
        return iUserService.findById(id);
    }
}
