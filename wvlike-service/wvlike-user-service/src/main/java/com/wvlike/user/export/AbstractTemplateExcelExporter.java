package com.wvlike.user.export;

import com.alibaba.excel.EasyExcel;
import com.ismyself.common.base.exception.CommonException;
import com.ismyself.common.base.result.ResultCode;
import com.ismyself.common.core.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Date: 2024/10/22
 * @Author: tuxinwen
 * @Description: Excel模板导出抽象类 T：入参类型 D：dataList类型
 */
@Slf4j
public abstract class AbstractTemplateExcelExporter<T extends BaseExcelParam, D> extends BaseAbstractExcelExporter<T, D> {


    /**
     * @return
     */
    protected abstract TemplateExcelFileEnum getFileEnum();

    /**
     * 不需要表头配置
     */
    @Override
    public String[] headArrays() {
        return new String[0];
    }

    @Override
    public void export(T param, HttpServletResponse response) {
        String templateFileUrl = getTemplateFileUrl();
        String fileName = getFileName(param);
        StopWatch stopWatch = new StopWatch("Excel文件导出：" + fileName);
        stopWatch.start("开始导出");
        // 配置响应头
        configResponse(response, fileName);
        try {
            // 查询数据前校验
            beforeQueryDataValidate(param);

            // 获取数据
            List<D> dataList = dataList(param);

            // 数据校验，业务可自己校验，默认只校验dataList
            dataListValidate(dataList);
            stopWatch.stop();

            // 组装excel
            stopWatch.start("文件写入");
            easyExcelExport(response, dataList, templateFileUrl);
        } catch (Exception e) {
            log.error("AbstractTemplateExcelExport export error, {}", e.getMessage(), e);
            // 针对异常实现告警
            if (exceptionNoticeFlag()) {
                sendErrorMessage(e);
            }
            exportErrorExcelFile(response, e);
        } finally {
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        }
    }

    /**
     * 根据模板文件枚举获取模板文件url
     */
    protected String getTemplateFileUrl() {
        return "excel-url";
    }

    @Override
    protected void easyExcelExport(HttpServletResponse response, List<D> dataList, List<List<String>> headList) throws IOException {
        // 禁止调用
        throw new CommonException(ResultCode.FAIL);
    }

    /**
     * easyExcel导出
     */
    protected void easyExcelExport(HttpServletResponse response, List<D> dataList, String templateFileUrl) throws IOException {
        InputStream templateFileInputStream = FileUtils.getFileStreamByUrl(templateFileUrl);
        if (templateFileInputStream == null) {
            throw new CommonException(ResultCode.FAIL);
        }
        EasyExcel.write(response.getOutputStream())
                .withTemplate(templateFileInputStream)
                .sheet()
                .doFill(dataList);
    }

}
