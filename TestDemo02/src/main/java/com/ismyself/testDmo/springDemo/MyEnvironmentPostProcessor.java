package com.ismyself.testDmo.springDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * package com.ismyself.testDmo.springDemo;
 *
 * @auther txw
 * @create 2020-04-08  21:44
 * @description：
 */
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String fileName = "app.properties";
    private final Properties properties = new Properties();
    private static final String PROPERTY_SOURCE_NAME = "defaultProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("###########MyEnvironmentPostProcessor");
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Map<String, Object> propertyMap = new HashMap<>();
//        propertyMap.put("myName", "lizo");
//        MapPropertySource propertySource = new MapPropertySource(fileName,propertyMap);
        PropertySource propertySource = environment.getPropertySources().get(PROPERTY_SOURCE_NAME);
        if (propertySource == null) {
            propertySource = new PropertiesPropertySource(PROPERTY_SOURCE_NAME, properties);
        }
        environment.getPropertySources().addLast(propertySource);
    }
}