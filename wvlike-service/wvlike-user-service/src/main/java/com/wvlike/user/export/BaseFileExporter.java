package com.wvlike.user.export;

/**
 * @Date: 2024/1/5
 * @Author: tuxinwen
 * @Description: 基础文件导出
 */
public interface BaseFileExporter {

    /**
     * 文件名称
     *
     * @return
     */
    String fileName();

    /**
     * 文件后缀
     *
     * @return
     */
    String fileSuffix();

}
