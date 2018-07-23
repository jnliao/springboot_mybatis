package www.sh.com.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author liaojinneng
 * @date 2018/7/23
 */
@Data
public class UserVo {

    private static final long serialVersionUID = 1L;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private String account;

    private String nickName;

    private String email;

    private Integer age;

    private Date createTime;

    private Date updateTime;

    private Boolean enabled;
}
