package com.ismyself.testDmo.controller;

import com.ismyself.testDmo.other.TestMyThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * package com.ismyself.testDmo.controller;
 *
 * @auther txw
 * @create 2020-06-27  14:43
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/test2")
public class MyTestControllerCopy implements InitializingBean {

    @Autowired
    private TestMyThreadLocal testMyThreadLocal;

    @RequestMapping("/myThread")
    public void myThread() {
        for (int i = 0; i < 20; i++) {
            testMyThreadLocal.set(new HashMap());
        }
    }


    @Autowired
    private ConfigurableEnvironment environment;

    @RequestMapping("/setProperty")
    public void setProperty() {
        System.out.println(environment.getProperty("student.name"));
        Map<String, Object> map = new HashMap<>();
        map.put("student.name", "txw");
        PropertySource defaultProperties = environment.getPropertySources().get("defaultProperties");
        defaultProperties = new MapPropertySource("defaultProperties", map);

        environment.getPropertySources().addLast(defaultProperties);
        System.out.println(environment.getProperty("student.name"));
        System.setProperty("student.name", "eeee");
        System.out.println(environment.getProperty("student.name"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
