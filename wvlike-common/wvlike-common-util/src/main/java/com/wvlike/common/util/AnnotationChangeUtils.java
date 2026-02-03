package com.wvlike.common.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * package com.wvlike.testDmo.refact;
 *
 * @auther txw
 * @create 2020-11-26  21:14
 * @description：
 */
@UtilityClass
public class AnnotationChangeUtils {

    public static <A extends Annotation> void addAnnotation(Class<?> des, Class<A> aClass, A anno) {
        des.getDeclaredAnnotations();
        try {
            Class<?>[] declaredClasses = Class.class.getDeclaredClasses();
            Class<?> annotationDataClass = null;
            for (Class<?> declaredClass : declaredClasses) {
                if (declaredClass.getName().equals("java.lang.Class$AnnotationData")) {
                    annotationDataClass = declaredClass;
                    break;
                }
            }
            Field annotationDataField = Class.class.getDeclaredField("annotationData");
            annotationDataField.setAccessible(true);
            Field annotationsField = annotationDataClass.getDeclaredField("annotations");
            annotationsField.setAccessible(true);
            Field declaredAnnotationsField = annotationDataClass.getDeclaredField("declaredAnnotations");
            declaredAnnotationsField.setAccessible(true);
            Object annotationData = annotationDataField.get(des);
            Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) annotationDataField.get(annotationData);
            Map<Class<? extends Annotation>, Annotation> declaredAnnotations = (Map<Class<? extends Annotation>, Annotation>) declaredAnnotationsField.get(annotationData);
            if (annotations.equals(Collections.emptyMap())) {
                annotations = new LinkedHashMap<>();
                annotationsField.set(annotationData, annotations);
            }
            if (declaredAnnotations.equals(Collections.emptyMap())) {
                declaredAnnotations = new LinkedHashMap<>();
                declaredAnnotationsField.set(annotationData, declaredAnnotations);
            }
            annotations.put(aClass, anno);
            declaredAnnotations.put(aClass, anno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
