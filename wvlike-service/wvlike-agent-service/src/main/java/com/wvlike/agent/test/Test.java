package com.wvlike.agent.test;

import com.alibaba.fastjson.JSON;
import com.wvlike.agent.common.User;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        while (true) {
            User user = new User("zane", 10);
            System.out.println(JSON.toJSON(user));
            System.out.println(user.toString());
            System.out.println(user.getClass().getName());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
