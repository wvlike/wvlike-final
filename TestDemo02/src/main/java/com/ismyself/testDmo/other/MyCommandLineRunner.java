package com.ismyself.testDmo.other;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * package com.ismyself.testDmo.other;
 *
 * @auther txw
 * @create 2020-06-27
 * @description：
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println(args+"MyCommandLineRunner start");

    }
}
