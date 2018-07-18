package www.sh.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import www.sh.com.domain.User;
import www.sh.com.mapper.UserMapper;
import www.sh.com.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
}
