package www.sh.com.common;

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
     * 响应数据
     */
    private T data;
    /**
     * 提示信息编码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;
    /**
     *  错误响应
     */
    private Object responseError;



    /**
     * 通用页面提示信息响应对象
     */
    public ResultForWeb(Boolean isSuccess, Integer code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }

    /**
     * 简单响应成功
     */
    public static <T> ResultForWeb successCommon(){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(true);
        resultForWeb.setCode(200);
        resultForWeb.setMessage("成功");
        return resultForWeb;
    }

    /**
     * 简单响应失败
     */
    public static <T> ResultForWeb errorCommon(){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(false);
        resultForWeb.setCode(500);
        resultForWeb.setMessage("失败");
        return resultForWeb;
    }

    /**
     * 成功并返回数据
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
     * 返回失败信息
     */
    public static <T> ResultForWeb error(T object){
        ResultForWeb resultForWeb = new ResultForWeb();
        resultForWeb.setIsSuccess(false);

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

}