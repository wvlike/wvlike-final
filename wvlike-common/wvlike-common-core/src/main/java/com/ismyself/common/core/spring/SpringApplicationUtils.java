package com.ismyself.common.core.spring;

import lombok.experimental.UtilityClass;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.bootstrap.BootstrapImportSelectorConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * package com.ismyself.testDmo.myselfFramework.utils;
 *
 * @auther txw
 * @create 2021-01-05  20:36
 * @description：
 */
@UtilityClass
public class SpringApplicationUtils {

    private static Map<SpringApplication, Boolean> CACHED_APP = new HashMap<>(2);

    public static boolean isBootApplication(SpringApplication application) {
        return CACHED_APP.computeIfAbsent(application, app -> {
            if (app == null) {
                return false;
            }
            Set<Object> sources = app.getAllSources();
            if (CollectionUtils.isEmpty(sources) || sources.size() > 1) {
                return false;
            }
            Object next = sources.iterator().next();
            return !next.equals(BootstrapImportSelectorConfiguration.class);
        });
    }

    public static Class<?> getMainAppClass(SpringApplication application) {
        Assert.isTrue(isBootApplication(application), "application must not be bootstrap");
        return (Class) application.getAllSources().iterator().next();
    }

    private static Map<SpringApplication, List<String>> PACKAGE_CACHE = new HashMap<>();

    public static List<String> getBasePackages(SpringApplication application) {
        return PACKAGE_CACHE.computeIfAbsent(application, app -> {
            Class<?> mainApplicationClass = getMainAppClass(app);
            ComponentScan annotation = AnnotatedElementUtils.findMergedAnnotation(mainApplicationClass, ComponentScan.class);
            Set<String> packageSet = new LinkedHashSet<>();
            if (annotation != null) {
                if (annotation.value().length != 0) {
                    for (String s : annotation.value()) {
                        if (StringUtils.hasText(s)) {
                            packageSet.add(s);
                        }
                    }
                }
                if (annotation.basePackages().length != 0) {
                    for (String s : annotation.basePackages()) {
                        if (StringUtils.hasText(s)) {
                            packageSet.add(s);
                        }
                    }
                }
                if (annotation.basePackageClasses().length != 0) {
                    for (Class<?> aClass : annotation.basePackageClasses()) {
                        packageSet.add(aClass.getName());
                    }
                }
            }
            if (packageSet.isEmpty()) {
                packageSet.add(mainApplicationClass.getPackage().getName());
            }
            return new LinkedList<>(packageSet);
        });
    }


}
