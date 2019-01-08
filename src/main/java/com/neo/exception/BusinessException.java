package com.neo.exception;

/**
 * @author 27351
 */
public class BusinessException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
