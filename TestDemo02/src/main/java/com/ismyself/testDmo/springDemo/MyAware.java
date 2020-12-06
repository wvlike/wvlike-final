package com.ismyself.testDmo.springDemo;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * package com.ismyself.testDmo.springDemo;
 *
 * @auther txw
 * @create 2020-06-27  18:18
 * @description：
 */
public class MyAware implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
