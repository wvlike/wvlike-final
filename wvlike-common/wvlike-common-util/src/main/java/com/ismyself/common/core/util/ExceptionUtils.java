package com.ismyself.common.core.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * package com.ismyself.testDmo.myselfFramework.common.utils;
 *
 * @auther txw
 * @create 2021-02-19  14:40
 * @description：
 */
@UtilityClass
public class ExceptionUtils {

    public static Throwable getRealException(Throwable throwable) {
        if (throwable instanceof UndeclaredThrowableException) {
            return getRealException((UndeclaredThrowableException) throwable);
        } else if (throwable instanceof InvocationTargetException) {
            return getRealException((InvocationTargetException) throwable);
        }
        return throwable;
    }

    public static Throwable getRealException(UndeclaredThrowableException ex) {
        return getRealException(ex.getUndeclaredThrowable());
    }

    public static Throwable getRealException(InvocationTargetException ex) {
        return getRealException(ex.getTargetException());
    }

}
