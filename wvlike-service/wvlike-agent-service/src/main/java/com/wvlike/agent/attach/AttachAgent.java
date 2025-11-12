package com.wvlike.agent.attach;

import com.wvlike.agent.common.User;

import java.lang.instrument.Instrumentation;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description: 动态Agent启动类
 */
public class AttachAgent {

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        System.out.println("agentmain_开始动态加载jar包22222");
        inst.addTransformer(new AttachTransformer(), true);
        //指定需要转换的类，动态加载时这一步必不可少。否则你修改了字节码但是没进行显示转换 jvm是加载不到的，静态加载的话不需要这一步，因为是在类加载时，而动态加载是在jvm运行时

        inst.retransformClasses(User.class);
        System.out.println("agentmain_结束动态加载jar包22222");
        System.out.println();
    }

}
