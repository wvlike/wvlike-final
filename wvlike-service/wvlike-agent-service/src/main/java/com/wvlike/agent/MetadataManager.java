package com.wvlike.agent;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Date: 2025/11/10
 * @Author: tuxinwen
 * @Description:
 */
public class MetadataManager {
    private static final Map<Object, String> SEX_MAP =
            Collections.synchronizedMap(new WeakHashMap<>());

    public static void setSex(Object obj, String sex) {
        SEX_MAP.put(obj, sex);
    }

    public static String getSex(Object obj) {
        return SEX_MAP.getOrDefault(obj, "unknown");
    }
}