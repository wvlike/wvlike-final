package com.ismyself.testDmo.common.observer;

/**
 * package com.ismyself.testDmo.common.observer;
 *
 * @auther txw
 * @create 2020-07-12  19:48
 * @description：观察者模式
 */
public class TestObserver {

    public static void main(String[] args) {
        ObserverCore wvlike = new ObserverCore();
        UserA userA = new UserA(wvlike);
        UserB userB = new UserB(wvlike);
        wvlike.update("wvlike", 12, true);

    }

}
