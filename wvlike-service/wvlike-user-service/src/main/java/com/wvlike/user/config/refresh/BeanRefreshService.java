package com.wvlike.user.config.refresh;

import cn.hutool.core.util.StrUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Date: 2024/5/14
 * @Author: tuxinwen
 * @Description: 刷新bean服务，通过监听apollo配置变化，刷新bean
 */
@Slf4j
@Component
public class BeanRefreshService implements ApplicationContextAware {

    private static final String CHANGE_BEAN_PREFIX = "change.bean.prefix.";

    private static final String CHANGE_BEAN_FLAG = "1";

    private ApplicationContext applicationContext;

    @ApolloConfig
    private Config config;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        Set<String> changedKeys = changeEvent.changedKeys();
        for (String changedKey : changedKeys) {
            if (!changedKey.startsWith(CHANGE_BEAN_PREFIX)) {
                continue;
            }
            String value = config.getProperty(changedKey, null);
            if (CHANGE_BEAN_FLAG.equals(value)) {
                continue;
            }
            String beanName = StrUtil.subAfter(changedKey, CHANGE_BEAN_PREFIX, true);
            this.refreshBean(beanName);
        }
    }

    public void refreshBean(String beanName) {
        // 刷新bean
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Class<?> aClass = beanFactory.getBean(beanName).getClass();
        // 销毁bean
        if (beanFactory.containsBean(beanName)) {
            beanFactory.destroySingleton(beanName);
        }
        // 替换为你的Bean类型
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
