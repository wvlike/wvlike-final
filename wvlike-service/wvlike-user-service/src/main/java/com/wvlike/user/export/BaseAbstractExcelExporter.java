package com.wvlike.user.export;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.wvlike.common.base.exception.CommonException;
import com.wvlike.common.base.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2024/10/21
 * @Author: tuxinwen
 * @Description: Excel导出基础抽象类 T：入参类型 D：dataList类型
 */
@Slf4j
public abstract class BaseAbstractExcelExporter<T extends BaseExcelParam, D> implements BaseExcelExporter<T, D> {

    protected static final String ZIP_SUFFIX = ".zip";
    protected static final String EXCEL2007_SUFFIX = ".xlsx";
    protected static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    protected static final String CONTENT_DISPOSITION = "Content-Disposition";
    protected static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    protected static final String DEFAULT_SHEET_NAME = "Sheet1";
    protected static final List<List<String>> ERROR_DATA_HEAD;

    static {
        ERROR_DATA_HEAD = Lists.newArrayList();
        ERROR_DATA_HEAD.add(Lists.newArrayList("导出失败"));
    }

    @Override
    public String fileName(T param) {
        return "";
    }

    @Override
    public String sheetName() {
        return DEFAULT_SHEET_NAME;
    }

    @Override
    public String fileSuffix() {
        return EXCEL2007_SUFFIX;
    }

    @Override
    public List<List<String>> headList() {
        return new ArrayList<>();
    }

    /**
     * 数据导出
     */
    @Override
    public void export(T param, HttpServletResponse response) {
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
            easyExcelExport(response, dataList, getHeadList());
        } catch (Exception e) {
            log.error("BaseAbstractExcelExport export error, {}", e.getMessage(), e);
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
     * 是否异常告警标识
     */
    protected abstract boolean exceptionNoticeFlag();

    /**
     * 导出校验，如校验数据是否超过2w行
     */
    protected abstract void beforeQueryDataValidate(T param);

    /**
     * easyExcel导出
     */
    protected abstract void easyExcelExport(HttpServletResponse response, List<D> dataList, List<List<String>> headList) throws IOException;

    /**
     * 获取文件名称
     */
    protected String getFileName(T param) {
        String fileName;
        if (StrUtil.isNotBlank(fileName())) {
            fileName = fileName();
        } else {
            try {
                fileName = fileName(param);
            } catch (Exception e) {
                log.error("BaseAbstractExcelExport getFileName error, {}", e.getMessage(), e);
                throw new CommonException(ResultCode.FAIL);
            }
        }
        if (StrUtil.isBlank(fileName)) {
            throw new CommonException(ResultCode.FAIL);
        }
        return fileName;
    }

    /**
     * 配置请求头
     */
    protected void configResponse(HttpServletResponse response, String fileName) {
        try {
            response.setContentType(EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, CONTENT_DISPOSITION);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=%s",
                            new String(URLEncoder.encode(fileName + fileSuffix(), StandardCharsets.UTF_8.name()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            log.error("BaseAbstractExcelExport configResponse error, {}", e.getMessage(), e);
            throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 配置错误文件请求头
     */
    protected void configErrorResponse(HttpServletResponse response) {
        try {
            String fileName = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_MS_PATTERN);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=%s",
                            new String(URLEncoder.encode(fileName + fileSuffix(), StandardCharsets.UTF_8.name()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            log.error("BaseAbstractExcelExport configErrorResponse error, {}", e.getMessage(), e);
            throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 数据集合校验
     */
    protected void dataListValidate(List<D> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 获取表头
     */
    protected List<List<String>> getHeadList() {
        if (ArrayUtil.isNotEmpty(headArrays())) {
            return Arrays.stream(headArrays())
                    .map(Lists::newArrayList)
                    .collect(Collectors.toList());
        }
        List<List<String>> headedList = headList();
        if (CollUtil.isNotEmpty(headedList)) {
            return headedList;
        }
        throw new CommonException(ResultCode.FAIL);
    }

    /**
     * 发生错误信息
     */
    protected void sendErrorMessage(Exception e) {

    }

    /**
     * 导出错误excel文件
     */
    protected void exportErrorExcelFile(HttpServletResponse response, Exception e) {
        // 生成业务异常错误文件
        try {
            List<List<String>> errorDataList = new ArrayList<>();
            errorDataList.add(Lists.newArrayList(e.getMessage()));
            EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .head(ERROR_DATA_HEAD)
                    .sheet(sheetName())
                    .doWrite(errorDataList);
        } catch (Exception e1) {
            log.error("BaseAbstractExcelExport export errorData error, {}", e1.getMessage(), e1);
            throw new CommonException(ResultCode.FAIL);
        }
    }

}
