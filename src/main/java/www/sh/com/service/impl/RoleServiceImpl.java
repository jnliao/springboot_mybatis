package www.sh.com.service.impl;

import www.sh.com.pojo.domain.Role;
import www.sh.com.mapper.RoleMapper;
import www.sh.com.service.IRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	
}
