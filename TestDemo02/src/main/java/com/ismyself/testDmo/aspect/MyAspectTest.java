package com.ismyself.testDmo.aspect;

import com.alibaba.fastjson.JSON;
import com.ismyself.testDmo.vo.UserDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * package com.ismyself.testDmo.aspect;
 *
 * @auther txw
 * @create 2020-02-11  11:31
 * @description：
 */
@Aspect
@Component
public class MyAspectTest {

    @Before("execution(* com.ismyself.testDmo.controller.MyTestController.*(..))")
    public void before(JoinPoint point) {
/*        UserDTO userDTO = (UserDTO) point.getArgs()[0];
        System.out.println(JSON.toJSONString(userDTO));*/
        System.out.println("before**********");
    }

    @After("execution(* com.ismyself.testDmo.controller.MyTestController.*(..))")
    public void after() {
        System.out.println("after**********");
    }

    @AfterReturning("execution(* com.ismyself.testDmo.controller.MyTestController.*(..))")
    public void afterReturning() {
        System.out.println("afterReturning**********");
    }

}
