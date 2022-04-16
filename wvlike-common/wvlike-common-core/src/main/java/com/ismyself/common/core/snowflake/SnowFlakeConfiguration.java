package com.ismyself.common.core.snowflake;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * package com.ismyself.testDmo.myselfFramework.utils;
 *
 * @auther txw
 * @create 2021-01-16  20:36
 * @description：
 */
@Configuration
public class SnowFlakeConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SnowFlakeIdGenerator snowFlakeIdGenerator(StringRedisTemplate redisTemplate, @Value("${spring.application.name}") String appName) {
        Long val = redisTemplate.opsForValue().increment("snow_flake_worker_id:" + appName);
        return new SnowFlakeIdGenerator(val);
    }

}
