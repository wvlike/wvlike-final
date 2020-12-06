package com.ismyself.testDmo;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


/**
 * package com.ismyself.testDmo;
 *
 * @auther txw
 * @create 2020-02-10  10:08
 * @description：
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableApolloConfig("application.yml")

@EnableMethodCache(basePackages = "com.company.mypackage")      //jetcache
@EnableCreateCacheAnnotation    //jetcache
public class TestDemoApp implements EnvironmentPostProcessor {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestDemoApp.class);
        Environment environment = context.getEnvironment();
        String myName = environment.getProperty("myName");
        System.out.println("######" + myName);

        DataSource bean = context.getBean(DataSource.class);
        System.out.println(bean);
    }


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("student.name","txw2");
//        PropertySource propertySource = new MapPropertySource("defaultProperties",map);
//        environment.getPropertySources().addLast(propertySource);
    }



}
