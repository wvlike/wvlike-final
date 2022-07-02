package com.wvlike.msg;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * package com.wvlike.msg;
 *
 * @auther txw
 * @create 2022-07-02  10:40
 * @description：
 */

@ServletComponentScan("com.wvlike")   //让@WebFilter等servlet相关注解生效
@SpringBootApplication
@EnableDiscoveryClient          //各种注册中心
//@EnableApolloConfig("application.yml")
@EnableFeignClients             //feign
@EnableMethodCache(basePackages = "com.company.mypackage")      //jetcache
@EnableCreateCacheAnnotation    //jetcache
@EnableCircuitBreaker           //hystix
@EnableScheduling               //开启定时任务
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class);
    }

}