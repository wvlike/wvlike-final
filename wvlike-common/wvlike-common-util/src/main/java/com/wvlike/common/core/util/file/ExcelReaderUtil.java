package com.wvlike.common.core.util.file;

import com.wvlike.common.base.constants.ExcelConstant;
import com.wvlike.common.core.util.file.excel.ExcelReadDataDelegated;
import com.wvlike.common.core.util.file.excel.ExcelXlsxReaderWithDefaultHandler;

import java.util.List;

/**
 * @Author: xinwen_tu
 * @Date: 2026/1/12
 * Description:
 */
public class ExcelReaderUtil {

    public static void readExcel(String filePath, ExcelReadDataDelegated excelReadDataDelegated) throws Exception {
        int totalRows = 0;
        if (filePath.endsWith(ExcelConstant.EXCEL07_EXTENSION)) {
            ExcelXlsxReaderWithDefaultHandler excelXlsxReader = new ExcelXlsxReaderWithDefaultHandler(excelReadDataDelegated);
            totalRows = excelXlsxReader.process(filePath);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xlsx!");
        }
        System.out.println("读取的数据总行数：" + totalRows);
    }


    public static void main(String[] args) throws Exception {
        String path = "D:\\test\\bigData07.xlsx";
        ExcelReaderUtil.readExcel(path, new ExcelReadDataDelegated() {
            @Override
            public void readExcelDate(int sheetIndex, int totalRowCount, int curRow, List<String> cellList) {
                System.out.println("总行数为：" + totalRowCount + " 行号为：" + curRow + " 数据：" + cellList);
            }
        });
    }

}
