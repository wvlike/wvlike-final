package com.ismyself.testDmo.other;

import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * package com.ismyself.testDmo.other;
 *
 * @auther txw
 * @create 2020-06-27  14:07
 * @description：
 */
@Component
public class TestMyThreadLocal {

    @Override
    protected void finalize() {
        this.remove();
    }

    public static ThreadLocal<Map<String, String>> threadLocal = new NamedThreadLocal("myTest") {
        @Override
        protected Object initialValue() {
            return new HashMap<String, String>();
        }
    };


    public Map<String, String> getValue() {
        return threadLocal.get();
    }

    public void set(Map map) {
        threadLocal.set(map);
    }

    public void remove() {
        threadLocal.remove();
    }

}
