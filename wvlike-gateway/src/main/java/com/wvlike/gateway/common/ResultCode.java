package com.wvlike.gateway.common;

/**
 * package com.ismyself.testDmo.common;
 *
 * @auther txw
 * @create 2020-02-11  11:23
 * @description：
 */
public enum ResultCode {

    SUCCESS(100, "success"),
    FAIL(-1,"fail"),
    ERRORPARAM(101,"error_param")
    ;

    private Integer code;
    private String messge;

    private ResultCode(Integer code, String messge) {
        this.code = code;
        this.messge = messge;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessge() {
        return messge;
    }
}
