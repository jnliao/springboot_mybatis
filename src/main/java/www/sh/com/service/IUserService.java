package www.sh.com.service;

import com.baomidou.mybatisplus.service.IService;
import www.sh.com.common.ResultForWeb;
import www.sh.com.pojo.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
public interface IUserService extends IService<User> {

    ResultForWeb findById(Long id);
	
}
