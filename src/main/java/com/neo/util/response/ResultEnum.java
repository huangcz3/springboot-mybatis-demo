package com.neo.util.response;

/**
 * 返回结果枚举.
 *
 * @author RUOK
 * @date 2017/6/15
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    NOT_LOGIN(1, "未登录或登录超时"),
    PERMISSION_DENIED(2, "无相关权限"),
    MAX_UPLOAD_SIZE_EXCEEDED(3, "上传文件大小超过限制"),
    UNKNOWN_FILE_TYPE(4, "未知文件类型"),
    FILE_IS_BLANK(5, "空白文件"),
    NO_VALID_DATA(6, "无有效数据"),
    FILE_TYPE_NOT_MATCH(7, "文件类型不匹配"),
    MPP_TABLE_NOT_EXIST(8, "表不存在！"),
    UNKNOWN_ERROR(-1, "未知错误"),
    NOT_EFFECTIVE_URL(-2, "链接无效，请重新获取！"),
    IS_REPEAT_LOGIN(-3, "已登录！"),
    INFO_EXIST(-4, "信息已存在");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
