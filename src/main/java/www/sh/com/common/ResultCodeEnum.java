package www.sh.com.common;

/**
 *
 * @author Administrator
 */
public enum ResultCodeEnum {

    /** system **/
    EXECUTEING_ERROR(404, "执行出错"),
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),

    /** 系统权限验证 **/
    NOT_FOUND_COOKIE(1001, "用户不存在"),
    USERINFO_EXPIRED(1002, "登录人数据过期"),
    NOT_FOUND_USERINFO(1003, "找不到当前登录人信息"),

    /** 业务数据问题**/
    SAVE_VALID_ERROR(1101, "数据验证失败"),
    DATA_CONSTRAINT(1102, "违法数据约束"),
    STATE_NOT_MATCH(1103, "当前状态不可操作"),
    ALREADY_EXISTED(1104, "数据已经存在"),

    /** api参数部分 **/
    PARAMETER_ERROR(1201, "参数错误"),
    MISSING_REQUIRED_ARGUMENTS(1202, "缺少必选参数"),
    INVALID_FORMAT(1203, "无效数据格式"),

    /** 交互部分 **/
    AUTH_FAIL(1301, "用户权限不足"),
    NOT_EXIST(1302, "操作数据不存在"),
    UPLOAD_FAIL(1304, "资源上传失败"),
    SEND_TIMEOUNT(1305, "发送超时");


    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
