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
 * 角色表
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Data
@Accessors(chain = true)
@TableName("role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 角色编码
     */
	@TableField("role_code")
	private String roleCode;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 是否删除，默认为否
     */
	@TableField("is_deleted")
	private Boolean deleted;


}
