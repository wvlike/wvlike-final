package com.ismyself.common.core.spring;

import lombok.experimental.UtilityClass;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * package com.ismyself.testDmo.myselfFramework.common.utils;
 *
 * @auther txw
 * @create 2021-02-18  14:39
 * @description：
 */
@UtilityClass
public class PropertySourceUtils {

    private static final String AOPS_PROPERTIES = "aops.properties";
    private static final String PRIORITY_PROPERTIES = "priority.properties";
    private static final String INNER_PROPERTIES = "inner.properties";

    public static void put(ConfigurableEnvironment environment, String name, Object o) {
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        location.put(name, o);
    }

    public static Object get(ConfigurableEnvironment environment, String name) {
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        return location.get(name);
    }


    public static String getAppName(Environment environment) {
        String appName = environment.getProperty("spring.application.name");
        Assert.notNull(appName, "spring.application.name must not be null");
        return appName;
    }

    private static final String SCAN_PACKGES = "aops_scan_packages";

    public static List<String> getBasePackages(ConfigurableEnvironment environment) {
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        return (List<String>) location.get(SCAN_PACKGES);
    }

    private static Map<String, Object> prepareOrGetInnerLocation(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, INNER_PROPERTIES ,MutablePropertySources::addLast);
    }

    public static void setBasePackages(ConfigurableEnvironment environment, List<String> packages) {
        Map<String, Object> location = prepareOrGetInnerLocation(environment);
        location.put(SCAN_PACKGES, packages);
    }

    protected static final String EXCLUDE_NAME = "spring.autoconfigure.exclude";

    public static void excludeAutoConfiguration(ConfigurableEnvironment environment, String name) {
        String old = environment.getProperty(EXCLUDE_NAME);
        if (StringUtils.hasText(old)) {
            putPriority(environment, EXCLUDE_NAME, old + "," + name);
        } else {
            putPriority(environment, EXCLUDE_NAME, name);
        }
    }

    private static Map<String, Object> prepareOrGetDefaultLocation(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, AOPS_PROPERTIES, MutablePropertySources::addLast);
    }


    public static void putPriority(ConfigurableEnvironment environment, String name, Object o) {
        Map<String, Object> location = prepareOrGetPriortyLocation(environment);
        location.put(name, o);
    }

    public static Map<String, Object> prepareOrGetPriortyLocation(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, PRIORITY_PROPERTIES, MutablePropertySources::addFirst);
    }

    private static Map<String, Object> prepareOrGetMapSource(ConfigurableEnvironment environment, String sourceName, BiConsumer<MutablePropertySources, MapPropertySource> sourceLocFunction) {
        MutablePropertySources propertySources = environment.getPropertySources();
        MapPropertySource mapPropertySource = (MapPropertySource) propertySources.get(sourceName);
        Map<String, Object> source;
        if (mapPropertySource == null) {
            source = new HashMap<>();
            mapPropertySource = new MapPropertySource(sourceName, source);
            sourceLocFunction.accept(propertySources, mapPropertySource);
        } else {
            source = mapPropertySource.getSource();
        }
        return source;
    }
}
