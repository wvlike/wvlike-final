package com.ismyself.testDmo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * package com.ismyself.testDmo.configuration;
 *
 * @auther txw
 * @create 2020-03-30  22:24
 * @description：
 */
@Data
@ConfigurationProperties("spring.datasource")
public class TxwConfig {

    private String className;

    private Map<String ,Object> dataSourceMap;

}
