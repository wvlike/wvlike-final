package com.wvlike.common.core.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * package com.wvlike.testDmo.myselfFramework.utils;
 *
 * @auther txw
 * @create 2021-02-16  19:48
 * @description：
 */
@Slf4j
@Component
public class RedisUtils {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /***************    time     ****************/

    public static void expire(String key, Long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisUtils expire error e:", e);
        }
    }

    public static void expire(String key, Long time, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.error("RedisUtils expire error e:", e);
        }
    }


    /***************    string     ****************/

    /**
     * get
     */
    public static String getString(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("RedisUtils getString error e:", e);
            return null;
        }
    }

    /**
     * set
     */
    public static void setString(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("RedisUtils setString error e:", e);
        }
    }

    /**
     * set 并指定时间 单位秒
     */
    public static void setString(String key, String value, Long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisUtils setString error e:", e);
        }
    }

    /**
     * set 并指定时间 单位
     */
    public static void setString(String key, String value, Long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
            log.error("RedisUtils setString error e:", e);
        }
    }

    /**
     * 加 1
     * 相当于  incr
     */
    public static Long incr(String key) {
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            log.error("RedisUtils incr error e:", e);
            return null;
        }
    }

    /**
     * 加 指定值
     * 相当于  incrBy
     */
    public static Long incr(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("RedisUtils incr error e:", e);
            return null;
        }
    }


    /**
     * 减 1
     * 相当于  decr
     */
    public static Long decr(String key) {
        try {
            return redisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            log.error("RedisUtils decr error e:", e);
            return null;
        }
    }

    /**
     * 减 指定值
     * 相当于  decrBy
     */
    public static Long decr(String key, long delta) {
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            log.error("RedisUtils decr error e:", e);
            return null;
        }
    }


    /***************    set     ****************/


    /***************    hash     ****************/


    public static void hSet(String key, String item, String value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
        } catch (Exception e) {
            log.error("RedisUtils decr error e:", e);
        }
    }

    /***************    list     ****************/


    /***************    zset     ****************/


}
