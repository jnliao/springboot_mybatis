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
 * 
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-18
 */
@Data
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

	private String account;

	private String password;

	@TableField("nick_name")
	private String nickName;

	private String email;

	private Integer age;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;
	/**
	 * 账号是否可用(默认是可用的)
	 */
	private Boolean enabled;

}
