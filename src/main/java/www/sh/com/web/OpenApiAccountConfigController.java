package www.sh.com.web;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-20
 */
@RestController
@RequestMapping("/openapiAccountConfig")
@Api(value = "user", description = "用户")
public class OpenApiAccountConfigController {
	
}
