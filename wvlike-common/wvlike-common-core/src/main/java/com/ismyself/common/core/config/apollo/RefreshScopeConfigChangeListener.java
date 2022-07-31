package com.ismyself.common.core.config.apollo;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ismyself.common.base.constants.ApolloConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Objects;
import java.util.Set;

/**
 * package com.ismyself.testDmo.myselfFramework.apollo;
 *
 * @auther txw
 * @create 2020-12-13  17:16
 * @description：
 */
@Slf4j
@Configuration
public class RefreshScopeConfigChangeListener implements ApplicationContextAware, Ordered {

    private ApplicationContext context;

    /**
     * @param changeEvent 配置改动事件
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
