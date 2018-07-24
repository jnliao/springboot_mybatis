package www.sh.com.service.impl;

import www.sh.com.pojo.domain.Permission;
import www.sh.com.mapper.PermissionMapper;
import www.sh.com.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
	
}
