package com.wvlike.user.export;

import lombok.Getter;

/**
 * @Date: 2024/10/22
 * @Author: tuxinwen
 * @Description: 模板Excel文件枚举
 */
@Getter
public enum TemplateExcelFileEnum {

    /**
     * excel模板文件枚举
     */
    EXAMPLE("example", "举例")

    ;

    /**
     * 值
     */
    private final String value;
    /**
     * name
     */
    private final String name;

    TemplateExcelFileEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
