package com.wvlike.common.core.config.apollo;

import cn.hutool.core.util.StrUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.wvlike.common.base.constants.ApolloConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * package com.wvlike.testDmo.myselfFramework.apollo;
 *
 * @auther txw
 * @create 2020-12-13  17:16
 * @description：
 */
@Slf4j
@Configuration
public class RefreshScopeConfigChangeListener implements ApplicationContextAware, Ordered {

    private ApplicationContext context;

    private final RefreshScope refreshScope;

    public RefreshScopeConfigChangeListener(final RefreshScope refreshScope) {
        this.refreshScope = refreshScope;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    // 1、监听配置变化

    /**
     * @param changeEvent 配置改动事件
     *(interestedKeyPrefixes = {"xxx.xxx"}   指定属性前缀
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        String namespace = changeEvent.getNamespace();
        if (!namespace.equals(ApolloConstants.COMMIN_NAME_SPACE)) {
            return;
        }
        Set<String> changedKeys = changeEvent.changedKeys();
        try {
            refreshProperties(changedKeys);
            changedKeys.forEach(key -> {
                ConfigChange change = changeEvent.getChange(key);
                if (Objects.nonNull(change)) {
                    log.info("application properties refresh | namespace: {} | key: {} | old value: {} | new value: {}", change.getNamespace(), key, change.getOldValue(), change.getNewValue());
                }
            });
        } catch (Exception e) {
            log.error("application properties refresh fail.");
        }
    }

    private void refreshProperties(Set<String> changedKeys) {
        this.context.publishEvent(new EnvironmentChangeEvent(changedKeys));
        //        context.getBean(RefreshScope.class).refreshAll();

    }

    // 2、监听logging.level的变化，进而更改指定日志级别

    private static final String LOGGING_PREFIX = "logging.level.";
    private static final String ROOT = LoggingSystem.ROOT_LOGGER_NAME;
    private static final String SPLIT = ".";

    @Resource
    private LoggingSystem loggingSystem;

    /**
     * 这里的 Config 默认是获取 namespace 为 application.properties，如果监听其他配置文件则需指定 namespace
     */
    @ApolloConfig
    private Config config;

    @PostConstruct
    private void init() {
        refreshLoggingLevels(config.getPropertyNames());
    }

    private void refreshLoggingLevels(Set<String> changedKeys) {
        for (String key : changedKeys) {
            if (containsIgnoreCase(key, LOGGING_PREFIX)) {
                String loggerName = LOGGING_PREFIX.equalsIgnoreCase(key) ? ROOT : key.substring(LOGGING_PREFIX.length());
                String strLevel = config.getProperty(key, parentStrLevel(loggerName));
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                // 通过此方法动态改变日志级别
                loggingSystem.setLogLevel(loggerName, level);
                log(loggerName, strLevel);
            }
        }
    }

    private static boolean containsIgnoreCase(String str, String searchStr) {
        if (StrUtil.isBlank(str) || StrUtil.isBlank(searchStr)) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    private String parentStrLevel(String loggerName) {
        String parentLoggerName = loggerName.contains(SPLIT) ? loggerName.substring(0, loggerName.lastIndexOf(SPLIT)) : ROOT;
        return loggingSystem.getLoggerConfiguration(parentLoggerName).getEffectiveLevel().name();
    }

    /**
     * 获取当前类的Logger对象有效日志级别对应的方法，进行日志输出。举例：
     * 如果当前类的EffectiveLevel为WARN，则获取的Method为 `org.slf4j.Logger#warn(java.lang.String, java.lang.Object, java.lang.Object)`
     * 目的是为了输出`changed {} log level to:{}`这一行日志
     */
    private void log(String loggerName, String strLevel) {
        try {
            LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(log.getName());
            Method method = log.getClass().getMethod(loggerConfiguration.getEffectiveLevel().name().toLowerCase(), String.class, Object.class, Object.class);
            method.invoke(log, "refreshConfig changed {} log level to:{}", loggerName, strLevel);
        } catch (Exception e) {
            log.error("refreshConfig changed {} log level to:{} error", loggerName, strLevel, e);
        }
    }

}
