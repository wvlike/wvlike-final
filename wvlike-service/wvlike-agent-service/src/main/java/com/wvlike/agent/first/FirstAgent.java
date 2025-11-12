package com.wvlike.agent.first;


import java.lang.instrument.Instrumentation;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description: 静态Agent启动类
 */
public class FirstAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("FirstAgent is Start.");
        inst.addTransformer(new FirstTransformer());
    }

}
