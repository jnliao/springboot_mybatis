package www.sh.com.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultForWeb<T> {

    /**
     * 是否执行成功
     */
    private Boolean isSuccess;

    /**
     * 响应数据对象
     */
    private T data;

    /**
     *  错误响应
     */
    private Object responseError;


    private String checkedKey;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 提示信息编码
     */
    @JSONField(name = "code")
    private Integer code;

    /**
     * 通用页面提示信息响应对象
     * @param isSuccess
     * @param code
     */
    public ResultForWeb(Boolean isSuccess, Integer code) {
        isSuccess = isSuccess;
        this.code = code;
    }

    public static <T> ResultForWeb error(T object){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(false);
        resultForWeb.setData(null);
        if (object instanceof String) {
            resultForWeb.setResponseError(object.toString());
        }
        if (object instanceof ResultCodeEnum) {
            resultForWeb.setCode(((ResultCodeEnum)object).getCode());
            resultForWeb.setMessage(((ResultCodeEnum)object).getMessage());
            return resultForWeb;
        }
        if (object instanceof ResponseError){
            resultForWeb.setResponseError(object);
        }
        resultForWeb.setMessage("失败");
        return resultForWeb;
    }

    /**
     * 成功并返回数据
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ResultForWeb success(T object){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(true);
        resultForWeb.setCode(200);
        resultForWeb.setMessage("成功");
        resultForWeb.setData(object);
        return resultForWeb;
    }

    /**
     * 响应成功
     * @param <T>
     * @return
     */
    public static <T> ResultForWeb successCommon(){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(true);
        resultForWeb.setCode(200);
        resultForWeb.setMessage("成功");
        return resultForWeb;
    }

    /**
     * 响应失败
     * @param <T>
     * @return
     */
    public static <T> ResultForWeb errorCommon(){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(false);
        resultForWeb.setCode(500);
        resultForWeb.setMessage("失败");
        return resultForWeb;
    }
}