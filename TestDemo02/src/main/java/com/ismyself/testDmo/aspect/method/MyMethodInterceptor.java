package com.ismyself.testDmo.aspect.method;

import com.ismyself.testDmo.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.UUID;

/**
 * package com.ismyself.testDmo.aspect.method;
 *
 * @auther txw
 * @create 2020-09-06  11:12
 * @description：
 */
@Slf4j
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        try {
            result = methodInvocation.proceed();
            if (result instanceof ResultDTO) {
                return result;
            }
        } catch (Throwable throwable) {
            log.error("MethodInterceptor e:", throwable.getMessage());
        }
        return ResultDTO.success(result);
    }

}
