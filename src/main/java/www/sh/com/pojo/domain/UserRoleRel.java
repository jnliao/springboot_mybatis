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
 * 用户角色表
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Data
@Accessors(chain = true)
@TableName("user_role_rel")
public class UserRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 用户id
     */
	@JSONField(serializeUsing = ToStringSerializer.class)
	@TableField("user_id")
	private Long userId;
    /**
     * 角色id
     */
	@JSONField(serializeUsing = ToStringSerializer.class)
	@TableField("role_id")
	private Long roleId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


}
