package com.ismyself.eurekaDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * package com.ismyself.eurekaDemo;
 *
 * @auther txw
 * @create 2020-02-10  10:10
 * @description：
 */
@SpringBootApplication
@EnableEurekaServer//开启注册中心服务
public class EurekaDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaDemoApp.class);
    }
}
