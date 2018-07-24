package www.sh.com.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liaojinneng
 * @date 2018/7/23
 */
@Data
public class UserVo {

    private static final long serialVersionUID = 1L;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否删除，默认为否
     */
    private Boolean deleted;

    /**
     * 权限编码列表
     */
    private List<String> permissionCodes;

}
