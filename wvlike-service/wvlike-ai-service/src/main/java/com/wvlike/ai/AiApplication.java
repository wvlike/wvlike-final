package com.wvlike.ai;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xinwen_tu
 * @Date: 2026/2/3
 * Description:
 */
@ComponentScan("com.wvlike")
@SpringBootApplication
@EnableDiscoveryClient          //各种注册中心
@EnableApolloConfig("application.yml")
@EnableFeignClients(basePackages = {"com.wvlike"})             //feign
@EnableCreateCacheAnnotation    //jetcache
@EnableCircuitBreaker           //hystix
public class AiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class);
    }
}
