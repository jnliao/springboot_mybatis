package www.sh.com.service;

import www.sh.com.pojo.domain.User;
import com.baomidou.mybatisplus.service.IService;
import www.sh.com.pojo.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
public interface IUserService extends IService<User> {

    UserVo findById(Long id);
	
}
