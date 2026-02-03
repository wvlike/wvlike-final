package com.wvlike.common.base.exception;

import com.wvlike.common.base.result.ResultCode;


/**
 * package com.wvlike.testDmo.common.code;
 *
 * @auther txw
 * @create 2021-02-05  15:57
 * @description：
 */
public class CommonException extends RuntimeException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommonException(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public CommonException(String message) {
        this(ResultCode.FAIL.getCode(), message);
    }

    public CommonException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessge());
    }

    public static CommonException getInstance(Integer code, String message) {
        return new CommonException(code, message);
    }

    public static CommonException getInstance(ResultCode resultCode) {
        return new CommonException(resultCode);
    }

}
