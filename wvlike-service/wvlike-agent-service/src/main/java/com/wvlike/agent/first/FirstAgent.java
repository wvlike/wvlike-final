package com.wvlike.agent.first;


import java.lang.instrument.Instrumentation;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description: 静态Agent启动类
 */
public class FirstAgent {

    /**
     * 在启动JVM时，通过-javaagent参数指定Agent的jar包路径。
     * -javaagent:D:\workspace\wvlike-final\wvlike-service\wvlike-agent-service\target\wvlike-agent-service-1.0-SNAPSHOT-jar-with-dependencies.jar
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("FirstAgent is Start.");
        inst.addTransformer(new FirstTransformer());
    }

}
