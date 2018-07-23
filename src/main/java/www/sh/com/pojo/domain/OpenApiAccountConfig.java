package www.sh.com.pojo.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-20
 */
@Data
@Accessors(chain = true)
@TableName("open_api_account_config")
public class OpenApiAccountConfig implements Serializable {

    private static final long serialVersionUID = 1L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 账号
     */
	private String account;
    /**
     * 秘钥
     */
	@TableField("api_key")
	private String apiKey;
    /**
     * 允许访问的接口
     */
	@TableField("api_urls")
	private String apiUrls;
    /**
     * 账号是否可用(默认是可用的)
     */
	private Boolean enabled;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;


}
