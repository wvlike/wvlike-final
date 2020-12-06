package com.ismyself.testDmo.aspect.method;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * package com.ismyself.testDmo.aspect.method;
 *
 * @auther txw
 * @create 2020-09-06  11:19
 * @description：
 */
@Configuration
@ConditionalOnMissingBean(DefaultPointcutAdvisor.class)
public class MyMethodConfig {

    @Value("${method.test:com.ismyself.testDmo.controller.MyTestController..*.*(..)}")
    private String pattern;

    @Bean("resultAop")
    public DefaultPointcutAdvisor resultAop() {
        DefaultPointcutAdvisor pfb = new DefaultPointcutAdvisor();
        JdkRegexpMethodPointcut j = new JdkRegexpMethodPointcut();
        j.setPattern(pattern);
        MyMethodInterceptor method = new MyMethodInterceptor();
        pfb.setAdvice(method);
        pfb.setPointcut(j);
        return pfb;
    }
}
