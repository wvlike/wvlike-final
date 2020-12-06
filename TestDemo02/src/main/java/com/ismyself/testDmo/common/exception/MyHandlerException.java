package com.ismyself.testDmo.common.exception;

import com.ismyself.testDmo.common.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * package com.ismyself.testDmo.common.exception;
 *
 * @auther txw
 * @create 2020-05-17  19:09
 * @description：
 */
@RestControllerAdvice
public class MyHandlerException {

    @ExceptionHandler(Exception.class)
    public ResultDTO cacthException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        return ResultDTO.fail(e.getMessage());

    }

}
