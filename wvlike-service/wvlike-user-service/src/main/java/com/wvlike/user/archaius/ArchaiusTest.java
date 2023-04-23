package com.wvlike.user.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesChainedProperty;
import org.apache.commons.configuration.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/3/6
 * @Author: tuxinwen
 * @Description:
 */
public class ArchaiusTest {


    public static void main(String[] args) throws Exception {

        test07();

    }

    private static void test07() throws Exception {
        HystrixDynamicProperty<String> personNameProperty = HystrixPropertiesChainedProperty.forString()
                .add("hystrix.command.coredy.personName", null)
                .add("hystrix.command.default.personName", "coredy")
                .build();
        System.out.println(personNameProperty.get());


    }

    private static void test06() throws Exception {
        PropertiesConfiguration configuration = new PropertiesConfiguration("test/app.properties");
        configuration.addConfigurationListener(event -> {
            System.out.println("是否更新之前：" + event.isBeforeUpdate());
            System.out.println("发生变化的属性名：" + event.getPropertyName());
            System.out.println("发生变化的属性值：" + event.getPropertyValue());
            System.out.println("事件类型：" + event.getType());
            System.out.println("事件源：" + event.getSource());
        });
        configuration.setProperty("change.name", "111");
    }

    private static void test05() throws Exception {
        EnvironmentConfiguration environmentConfiguration = new EnvironmentConfiguration();
        System.out.println(ConfigurationUtils.toString(environmentConfiguration));
    }

    private static void test04() throws Exception {
        SystemConfiguration systemConfiguration = new SystemConfiguration();
        System.out.println(ConfigurationUtils.toString(systemConfiguration));

        System.out.println(systemConfiguration.getString("user.name"));
    }

    private static void test03() throws Exception {
        Configuration propertiesConfiguration = new PropertiesConfiguration("test/app.properties");
        Configuration configuration = new PropertiesConfiguration("test/res.properties");
        //输出 当然还可以写到文件中 根据你传入流
        ConfigurationUtils.dump(propertiesConfiguration, System.out);
        System.out.println();
        //将propertiesConfiguration内容添加到configuration中
        ConfigurationUtils.append(propertiesConfiguration, configuration);
        ConfigurationUtils.dump(configuration, System.out);
        //该方法是CommonsConfiguration找文件的方法
        //如果用户家目录下面存在app.xml 就读取
        //如果家目录不存在 读取classpath下面的
        //如果都没有就报错
        System.out.println();
        URL locate = ConfigurationUtils.locate("test/app.xml");
        System.out.println(locate.toString());
    }

    private static void test02() throws Exception {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("test/app.properties");
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
