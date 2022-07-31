package com.wvlike.user.properties.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * package com.wvlike.user.properties.test;
 *
 * @auther txw
 * @create 2022-07-16  11:42
 * @description：
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "my.apollo.bean")
public class MyApolloProperties {

    private String name;

    private Integer code;

    private List<String> areas;

}
