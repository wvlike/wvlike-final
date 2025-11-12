package com.wvlike.agent.attach;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description:
 */
public class AttachTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        //只修改自定义的User类
        System.out.println("className222222:" + className);

        if (className.equals("com/wvlike/agent/common/User")) {
            System.out.println("开始修改User类");
            try {
                ClassPool pool = ClassPool.getDefault();
                pool.appendClassPath(new LoaderClassPath(loader));
                CtClass clazz = pool.makeClass(new ByteArrayInputStream(classfileBuffer));

                // ❌ 不再 addField / addMethod！
                // ✅ 改为：在构造函数或 toString 中使用外部存储

                // 示例：重写 toString，从 MetadataManager 获取 sex
                CtMethod toStringMethod = clazz.getDeclaredMethod("toString");
                toStringMethod.setBody(
                        "{ return \"User{\" +\n" +
                                "\"name='\" + this.name + '\\'' + \", \" +\n" +
                                "\"age=\" + this.age + \", \" +\n" +
                                "\"sex='\" + com.wvlike.agent.MetadataManager.getSex(this) + '\\'\' +\n" +
                                "\"}\"; }"
                );
                return clazz.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
