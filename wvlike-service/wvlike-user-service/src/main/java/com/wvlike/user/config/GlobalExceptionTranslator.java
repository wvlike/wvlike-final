package com.wvlike.user.config;

import com.wvlike.common.base.result.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: txw
 * Date: 2022-10-09
 * Description:
 */
@RestControllerAdvice
public class GlobalExceptionTranslator {

    @ExceptionHandler(Exception.class)
    public ResultDTO<String> handleError(Exception e) {
        return ResultDTO.fail(e.getMessage());
    }
    
}
