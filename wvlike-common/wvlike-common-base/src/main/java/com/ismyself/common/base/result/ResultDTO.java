package com.ismyself.common.base.result;

/**
 * package com.ismyself.demo.common;
 *
 * @auther txw
 * @create 2021-01-24  19:05
 * @description：
 */

public class ResultDTO {

    private Integer code;
    private String message;
    private Object data;

    public static ResultDTO success() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(ResultCode.SUCCESS.getCode());
        resultDTO.setMessage(ResultCode.SUCCESS.getMessge());
        return resultDTO;
    }

    public static ResultDTO success(Object o) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(ResultCode.SUCCESS.getCode());
        resultDTO.setMessage(ResultCode.SUCCESS.getMessge());
        resultDTO.setData(o);
        return resultDTO;
    }

    public static ResultDTO fail() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(ResultCode.FAIL.getCode());
        resultDTO.setMessage(ResultCode.FAIL.getMessge());
        return resultDTO;
    }

    public static ResultDTO fail(String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(ResultCode.FAIL.getCode());
        resultDTO.setMessage(message);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
