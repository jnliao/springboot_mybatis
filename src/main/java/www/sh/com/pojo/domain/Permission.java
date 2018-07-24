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
 * 权限表
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Data
@Accessors(chain = true)
@TableName("permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 模块id
     */
	@JSONField(serializeUsing = ToStringSerializer.class)
	@TableField("module_id")
	private Long moduleId;
    /**
     * 权限编码
     */
	@TableField("permission_code")
	private String permissionCode;
    /**
     * 权限名称
     */
	@TableField("permission_name")
	private String permissionName;
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
