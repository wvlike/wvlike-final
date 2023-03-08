package com.wvlike.user.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/3/6
 * @Author: tuxinwen
 * @Description:
 */
public class ArchaiusTest {


    public static void main(String[] args) throws Exception {

        test02();
    }

    private static void test02() throws Exception {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("app.properties");
        System.out.println(propertiesConfiguration.getString("app.test"));
    }

    private static void test01() throws InterruptedException {
        DynamicPropertyFactory instance = DynamicPropertyFactory.getInstance();
        DynamicStringProperty stringProperty = instance.getStringProperty("con1", "defalut");
        stringProperty.addCallback(() -> System.out.println("con1 属性值发生变化！"));
        while (true) {
            System.out.println(stringProperty.get());
            TimeUnit.SECONDS.sleep(10);
        }
    }

}
