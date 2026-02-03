package com.wvlike.common.core.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: txw
 * Date: 2022-10-17
 * Description: 对象参数验证工具, 解决非controller层数据校验问题， @Validated、@Valid
 */
public class ValidationUtils {

    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @throws IllegalArgumentException 校验不通过，则报业务异常
     */
    public static void validateEntity(Object object) throws IllegalArgumentException {
        validateEntity(object, Default.class);
    }
    
    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws IllegalArgumentException 校验不通过，则报业务异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws IllegalArgumentException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            String msg = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("||"));
            throw new IllegalArgumentException(msg);
        }
    }
}

