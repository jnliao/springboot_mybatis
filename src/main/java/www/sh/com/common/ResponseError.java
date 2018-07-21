package www.sh.com.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端错误信息描述
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    @JSONField(name = "Message")
    private String Message;

}
