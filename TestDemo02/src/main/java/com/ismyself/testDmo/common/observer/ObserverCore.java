package com.ismyself.testDmo.common.observer;

import java.io.Serializable;
import java.util.Observable;

/**
 * package com.ismyself.testDmo.common.observer;
 *
 * @auther txw
 * @create 2020-07-12
 * @description：
 */
public class ObserverCore extends Observable implements Serializable {

    private String name;
    private Integer num;

    @Override
    public String toString() {
        return "ObserverCore{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", flag=" + flag +
                '}';
    }

    private Boolean flag;

    public ObserverCore(String name, Integer num, Boolean flag) {
        this.name = name;
        this.num = num;
        this.flag = flag;

    }

    public ObserverCore() {
    }

    public String getName() {
        return name;
    }


    public Integer getNum() {
        return num;
    }


    public Boolean getFlag() {
        return flag;
    }

    public void update(String name, Integer num, Boolean flag) {
        this.name = name;
        this.num = num;
        this.flag = flag;
        this.setChanged();
        notifyObservers();
    }
}
