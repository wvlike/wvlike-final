package com.wvlike.user.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Date: 2024/1/5
 * @Author: tuxinwen
 * @Description: Excel没有实体导出抽象类 T：入参类型 D：dataList类型为 List<String>
 */
@Slf4j
public abstract class AbstractNoModelExcelExporter<T extends BaseExcelParam> extends BaseAbstractExcelExporter<T, List<String>> {

    @Override
    protected void easyExcelExport(HttpServletResponse response, List<List<String>> dataList, List<List<String>> headList) throws IOException {
        EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .head(getHeadList())
                .sheet(sheetName())
                .doWrite(dataList);
    }

}
