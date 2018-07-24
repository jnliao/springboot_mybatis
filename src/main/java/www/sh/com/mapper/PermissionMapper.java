package www.sh.com.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import www.sh.com.pojo.domain.Permission;

import java.util.List;

/**
 * <p>
  * 权限表 Mapper 接口
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询某用户拥有的权限（接口权限）
     * @param account 用户账号
     * @return 权限列表
     */
    public List<Permission> findPermissionsForUser( @Param("account")String account);

}