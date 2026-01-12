package com.wvlike.agent.test;


import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.File;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description:
 */
public class AttachAgentTest {

    public static void main(String[] args) throws Exception {

        // 根据进程id 与目标jvm程序建立 socket连接
        ByteBuddyAgent.attach(new File("D:\\workspace\\wvlike-final\\wvlike-service\\wvlike-agent-service\\target\\wvlike-agent-service-1.0-SNAPSHOT-jar-with-dependencies.jar"),
                "27276");

        // 使用下面 需要加 jna 和 jna-platform 依赖
//        VirtualMachine vm = ForHotSpot.attach("27292");
//        vm.loadAgent("D:\\workspace\\wvlike-final\\wvlike-service\\wvlike-agent-service\\target\\wvlike-agent-service-1.0-SNAPSHOT-jar-with-dependencies.jar");

        Thread.sleep(10000);

    }
}
