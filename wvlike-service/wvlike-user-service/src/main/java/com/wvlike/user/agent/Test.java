package com.wvlike.user.agent;

import com.alibaba.fastjson.JSON;

/**
 * @Date: 2023/4/19
 * @Author: tuxinwen
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        User user = new User("zane");
        System.out.println(JSON.toJSON(user));
        System.out.println(user.toString());
    }

}
