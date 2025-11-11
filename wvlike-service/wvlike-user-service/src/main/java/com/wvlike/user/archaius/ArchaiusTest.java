package com.wvlike.user.archaius;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesChainedProperty;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.configuration.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/3/6
 * @Author: tuxinwen
 * @Description:
 */
public class ArchaiusTest {


    public static void main(String[] args) throws Exception {

        System.out.println(1000 % 45);

    }

    private static void test10() throws Exception {
        int rowsNumber = 10;
        int columnsNumber = 10;
        int width = 4;
        for (int j = 0; j < rowsNumber; j++) {
            for (int i = 0; i < columnsNumber; i++) {
                int x = i * width + j * width, y = -i * width + j * width;
                System.out.println(x + "______" + y);
            }
        }
    }


    private static void test09() throws Exception {

        addVisibleWatermark("C:\\Users\\kingdee\\Desktop\\imgTest\\testImg1111.png", "C:\\Users\\kingdee\\Desktop\\imgTest\\testImg2222.png", "水印文字");


    }

    public static void addVisibleWatermark(String inputPath, String outputPath, String watermarkText) throws IOException {
        File inputFile = new File(inputPath);
        BufferedImage originalImage = ImageIO.read(inputFile);

        Graphics2D g2d = originalImage.createGraphics();

        // 设置字体、颜色、透明度
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f); // 15% 透明度
        g2d.setComposite(alpha);
        g2d.setColor(Color.RED);
        g2d.rotate(Math.toRadians(-45)); // 旋转-45度
        int fontSize = (originalImage.getWidth() + originalImage.getHeight()) / 40;
        Font font = new Font("微软雅黑", Font.BOLD, fontSize);

        g2d.setFont(font);

        // 水印位置（右下角）
//        int x = originalImage.getWidth() - 200;
//        int y = originalImage.getHeight() - 50;
//        g2d.drawString(watermarkText, x, y);


        int textWidth = 1000; // 自定义方法计算文本宽度[5](@ref)
        int xStep = textWidth + 50; // 水平间距
        int yStep = fontSize + 30;  // 垂直间距
        for (int i = 0; i < originalImage.getWidth(); i += xStep) {
            for (int j = fontSize; j < originalImage.getHeight(); j += yStep) {
                g2d.drawString(watermarkText, i, j);
            }
        }

        g2d.dispose();

        // 输出图片
        ImageIO.write(originalImage, "png", new File(outputPath));
    }




    private static void test08() throws Exception {
        Date creditTime = DateUtil.date(4904985600000L);
        String creditTimeStr = DateUtil.format(creditTime, "yyyy-MM-dd HH:mm:ss");
        System.out.println("creditTimeStr = " + creditTimeStr);
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
