package com.wvlike.user.export;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Date: 2024/1/5
 * @Author: tuxinwen
 * @Description: Excel导出 T：入参类型 D：dataList类型
 */
public interface BaseExcelExporter<T extends BaseExcelParam, D> extends BaseFileExporter {

    /**
     * 文件名称
     * 带参数
     *
     * @param param
     * @return
     */
    String fileName(T param);

    /**
     * sheet名称
     *
     * @return
     */
    String sheetName();

    /**
     * 表头
     * 当设置了表头，优先级会比@ExcelProperty注解优先级高
     *
     * @return
     */
    List<List<String>> headList();

    /**
     * 表头数组
     * 当设置了表头，优先级会比@ExcelProperty注解优先级高
     *
     * @return
     */
    String[] headArrays();

    /**
     * 数据集合
     * 可以是Bean（需要注解）、List<String>
     *
     * @param param
     * @return
     */
    List<D> dataList(T param);

    /**
     * 导出操作
     *
     * @param response
     * @param param
     */
    void export(T param, HttpServletResponse response);

}
