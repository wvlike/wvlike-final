package com.wvlike.test;

import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesChainedProperty;

/**
 * @Date: 2023/2/8
 * @Author: tuxinwen
 * @Description:
 */
public class HystrixTest {

    public static void main(String[] args) {
        test01();
    }

    public static void test01() {
        HystrixDynamicProperty<String> personNameProperty = HystrixPropertiesChainedProperty.forString()
                .add("hystrix.command.coredy.personName", "xxxxx")
                .add("hystrix.command.default.personName", "coredy")
                .add("hystrix.command.coredy.personName", "123")
                .build();
        System.out.println(personNameProperty.get());

    }

}
