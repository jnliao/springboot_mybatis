package www.sh.com.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import www.sh.com.domain.User;
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
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public User getCrmUsers(HttpServletRequest request,
                            @ApiParam(name = "id",value="用户id", required = true) @RequestParam("id") Long id) {
        return iUserService.findById(id);
    }
}
