package com.ismyself.common.base.result;

/**
 * package com.ismyself.demo.common;
 *
 * @auther txw
 * @create 2021-01-24  19:05
 * @description：
 */

public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultDTO<T> success() {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResultCode.SUCCESS.getCode());
        resultDTO.setMessage(ResultCode.SUCCESS.getMessge());
        return resultDTO;
    }

    public static <T> ResultDTO<T> success(T t) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResultCode.SUCCESS.getCode());
        resultDTO.setMessage(ResultCode.SUCCESS.getMessge());
        resultDTO.setData(t);
        return resultDTO;
    }

    public static <T> ResultDTO<T> fail() {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResultCode.FAIL.getCode());
        resultDTO.setMessage(ResultCode.FAIL.getMessge());
        return resultDTO;
    }

    public static <T> ResultDTO<T> fail(String message) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResultCode.FAIL.getCode());
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T> ResultDTO<T> fail(ResultCode resultCode) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(resultCode.getCode());
        resultDTO.setMessage(resultCode.getMessge());
        return resultDTO;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
