package www.sh.com.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import www.sh.com.common.CommonConstant;
import www.sh.com.common.ResultForWeb;
import www.sh.com.mapper.PermissionMapper;
import www.sh.com.pojo.domain.Permission;
import www.sh.com.pojo.domain.User;
import www.sh.com.pojo.vo.UserVo;
import www.sh.com.mapper.UserMapper;
import www.sh.com.service.ILoginservice;
import www.sh.com.util.RedisService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liaojinneng
 * @date 2018/7/21
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginservice{
    private static final String REDIS_USER_LOGIN = CommonConstant.UserConstant.REDIS_USER_LOGIN;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResultForWeb login(String account, String password) {
        User condition = new User();
        condition.setDeleted(false);
        condition.setAccount(account);

        try {
            EntityWrapper<User> entityWrapper = new EntityWrapper<>(condition);
            List<User> userList = userMapper.selectList(entityWrapper);
            if(userList==null || userList.size()<=0){
                return ResultForWeb.errorCommon();
            }

            User user = userList.get(0);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(password, user.getPassword())){
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user,userVo);

                List<Permission> permissionList = permissionMapper.findPermissionsForUser(user.getAccount());
                if(permissionList!=null && permissionList.size()>0){
                    ArrayList<String> permissionCodes = new ArrayList<>(20);
                    permissionList.forEach(obj->permissionCodes.add(obj.getPermissionCode()));
                    userVo.setPermissionCodes(permissionCodes);
                }

                String str = REDIS_USER_LOGIN + JSON.toJSONString(userVo) + System.currentTimeMillis();
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String accessToken = encoder.encode(str);
                String redisKey = REDIS_USER_LOGIN + accessToken;
                redisService.setex(redisKey, JSON.toJSONString(userVo), 28800);
                return ResultForWeb.success(accessToken);
            }
        } catch (Exception e) {
            log.error("LoginServiceImpl login has an error: "+e);
        }

        return ResultForWeb.errorCommon();
    }

    @Override
    public ResultForWeb logout(String accessToken) {
        try {
            redisService.del(REDIS_USER_LOGIN + accessToken);
            return ResultForWeb.success("注销成功");
        } catch (Exception e){
            log.error("LoginServiceImpl logout has error: ", e);
        }
        return ResultForWeb.errorCommon();
    }
}
