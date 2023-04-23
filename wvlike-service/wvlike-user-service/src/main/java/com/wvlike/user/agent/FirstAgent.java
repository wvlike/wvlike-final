package com.wvlike.user.agent;


import java.lang.instrument.Instrumentation;

/**
 * @Date: 2023/4/19
 * @Author: tuxinwen
 * @Description:
 */
public class FirstAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("FirstAgent is Start.");
        inst.addTransformer(new FirstTransformer());
    }
}
