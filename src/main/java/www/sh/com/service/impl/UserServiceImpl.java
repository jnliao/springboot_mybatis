package www.sh.com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.sh.com.common.ResultForWeb;
import www.sh.com.mapper.UserMapper;
import www.sh.com.pojo.domain.User;
import www.sh.com.pojo.vo.UserVo;
import www.sh.com.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ResultForWeb findById(Long id) {
        try {
            UserVo userVo = new UserVo();
            User user = userMapper.selectById(id);
            if(user != null){
                BeanUtils.copyProperties(user,userVo);
            }
            return ResultForWeb.success(userVo);
        } catch (BeansException e) {
            log.error("UserServiceImpl findById has an error: " + e);
        }
        return ResultForWeb.errorCommon();
    }
}
