package com.wvlike.test;

import com.google.common.collect.Lists;
import com.wvlike.user.agent.User;
import org.junit.Test;
import rx.Observable;

import java.util.List;

/**
 * @Date: 2023/6/27
 * @Author: tuxinwen
 * @Description:
 */
public class RxJavaTest {

    private final static List<User> userList;

    static {
        User u123 = new User("123", 123);
        User u456 = new User("456", 456);
        User u789 = new User("789", 789);
        User u369 = new User("369", 369);
        User u258 = new User("258", 258);
        User u147 = new User("147", 147);
        userList = Lists.newArrayList(u123, u456, u789, u369, u258, u147);
    }

    @Test
    public void test01() {
        Observable.from(userList).subscribe(System.out::println);
    }

}
