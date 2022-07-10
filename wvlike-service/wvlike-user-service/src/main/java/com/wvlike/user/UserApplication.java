package com.wvlike.user;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * package com.wvlike.user;
 *
 * @auther txw
 * @create 2022-06-30  20:41
 * @description：
 */
@ComponentScan("com.wvlike")
@ServletComponentScan("com.wvlike")   //让@WebFilter等servlet相关注解生效
@SpringBootApplication
@EnableDiscoveryClient          //各种注册中心
//@EnableApolloConfig("application.yml")
@EnableFeignClients(basePackages = {"com.wvlike"})             //feign
@EnableMethodCache(basePackages = "com.wvlike")      //jetcache
@EnableCreateCacheAnnotation    //jetcache
@EnableCircuitBreaker           //hystix
@EnableScheduling               //开启定时任务
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

}
