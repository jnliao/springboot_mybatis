package www.sh.com.pojo.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Data
@Accessors(chain = true)
@TableName("role_permission_rel")
public class RolePermissionRel implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 角色id
     */
	@JSONField(serializeUsing = ToStringSerializer.class)
	@TableField("role_id")
	private Long roleId;
    /**
     * 权限id
     */
	@JSONField(serializeUsing = ToStringSerializer.class)
	@TableField("permission_id")
	private Long permissionId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


}
