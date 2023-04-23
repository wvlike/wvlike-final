package com.wvlike.user.agent;

/**
 * @Date: 2023/4/19
 * @Author: tuxinwen
 * @Description:
 */

public class User {
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
