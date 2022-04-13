package com.ismyself.common.base.result;

import lombok.Builder;
import lombok.Data;

/**
 * package com.ismyself.demo.common;
 *
 * @auther txw
 * @create 2021-01-24  19:05
 * @description：
 */
@Data
@Builder
public class ResultDTO {

    private Integer code;
    private String message;
    private Object data;

    public static ResultDTO success(){
        return ResultDTO.builder().code(ResultCode.SUCCESS.getCode()).message(ResultCode.SUCCESS.getMessge()).data(null).build();
    }

    public static ResultDTO success(Object o){
        return ResultDTO.builder().code(ResultCode.SUCCESS.getCode()).message(ResultCode.SUCCESS.getMessge()).data(o).build();
    }

    public static ResultDTO fail(){
        return ResultDTO.builder().code(ResultCode.FAIL.getCode()).message(ResultCode.FAIL.getMessge()).data(null).build();
    }

    public static ResultDTO fail(String message){
        return ResultDTO.builder().code(ResultCode.FAIL.getCode()).message(message).data(null).build();
    }
}
