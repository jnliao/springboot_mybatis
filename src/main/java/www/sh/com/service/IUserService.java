package www.sh.com.service;

import www.sh.com.domain.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
public interface IUserService extends IService<User> {

    User findById(Long id);
	
}
