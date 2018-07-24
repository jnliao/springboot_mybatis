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
 * 对外接口权限配置表
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Data
@Accessors(chain = true)
@TableName("open_api_config")
public class OpenApiConfig implements Serializable {

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
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 是否删除，默认为否
     */
	@TableField("is_deleted")
	private Boolean deleted;


}
