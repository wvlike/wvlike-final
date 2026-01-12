package com.wvlike.user.export;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.ismyself.common.base.exception.CommonException;
import com.ismyself.common.base.result.ResultCode;
import com.ismyself.common.core.util.file.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Date: 2024/11/4
 * @Author: tuxinwen
 * @Description:
 */
@Slf4j
@Service
//@ExcelExporter(ExcelExporterEnum.INVITER_SETTLEMENT_EVIDENCE)
public class BizExampleExporter extends BaseAbstractExcelExporter<BaseExcelParam, Object> {

    private static final String SHEET0_NAME = "统计信息";

    private static final String SHEET1_NAME = "明细信息";

    private static final String SHEET2_NAME = "特殊佣金信息";

    private static final String SHEET3_NAME = "佣金扣减明细信息";

    private static final String INVITER_NAME = "代理人名称";

    private static final String APPROVAL_SERIAL_NUMBER = "审批单据流水号";

    private static final String APPROVAL_SERIAL_NUMBER_REMARK = "审批单据备注";

    private static final String WAITING_DEDUCTION_BONUS = "扣减佣金金额(元)";

    private static final String WAITING_SETTLEMENT_BONUS = "待结算佣金总金额(元)";

    private static final String SPECIAL_BONUS = "特殊结算佣金(元)";

    private static final String FILE_SUFFIX_NAME = "证据包";

    private static final String DATE_FORMAT = "MM";

    private static final String IMAGE_TYPE_PNG = "png";

    private static final String[] DETAIL_ROW_HEADER = {"订单编号", "企业名称", "申请人姓名", "产品名称", "审批通过金额(万元)", "审批通过时间", "放款金额(万元)", "放款时间", "活动名称", "佣金(元)"};

    private static final String[] DEDUCTION_ROW_HEADER = {"扣除日期", "产品名称", "扣减原因", "公司名称", "申请人姓名", "资方放款时间", "放款金额（万元）", "提前结清/逾期时间", "提前结清/逾期时间金额（元）", "一级代理商", "渠道经理", "扣减佣金金额（元）"};

    @Override
    public String[] headArrays() {
        return new String[0];
    }

    @Override
    public List<Object> dataList(BaseExcelParam param) {
        return Collections.emptyList();
    }

    @Override
    public void export(BaseExcelParam param, HttpServletResponse response) {
        try {
            StopWatch stopWatch = new StopWatch("Excel文件导出：代理人共享结算导出证据");
            stopWatch.start("开始导出");
            String fileName = "压缩导出例子";
            // 获取导出数据
            // 配置响应头
            configResponse(response, fileName);
            stopWatch.stop();

            // 组装excel
            stopWatch.start("文件写入：" + fileName);
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
                // excel文件的导出及压缩
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ExcelWriter excelWriter = null;
                try {
                    excelWriter = EasyExcel.write(outputStream)
                            .autoCloseStream(false)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                            .build();
                    zipOutputStream.putNextEntry(new ZipEntry(fileName + EXCEL2007_SUFFIX));
                    // 填充，支持多个sheet
                    supplementDetailInfo(excelWriter);
                } finally {
                    if (excelWriter != null) {
                        excelWriter.finish();
                    }
                }
                outputStream.writeTo(zipOutputStream);
                outputStream.flush();
                zipOutputStream.closeEntry();

                // 压缩其他文件
                Map<String, String> fileMap = new HashMap<>();
                // 设置压缩文件
                ZipUtils.setZipForStream(fileMap, zipOutputStream);

                zipOutputStream.finish();
            }
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        } catch (Exception e) {
            log.error("BaseAbstractExcelExport export error, {}", e.getMessage(), e);
            // 针对异常实现告警
            if (exceptionNoticeFlag()) {
                sendErrorMessage(e);
            }
            configErrorResponse(response);
            exportErrorExcelFile(response, e);
        }
    }


    /**
     * 配置响应头
     */
    @Override
    protected void configResponse(HttpServletResponse response, String fileName) {
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, CONTENT_DISPOSITION);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=%s",
                            new String(URLEncoder.encode(fileName + ZIP_SUFFIX, StandardCharsets.UTF_8.name()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            log.error("BaseAbstractExcelExport configResponse error, {}", e.getMessage(), e);
            throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 填充明细信息
     */
    private void supplementDetailInfo(ExcelWriter excelWriter) {
        List<List<String>> dataList = CollUtil.newArrayList();

        List<List<String>> head = Arrays.stream(DETAIL_ROW_HEADER)
                .map(Lists::newArrayList)
                .collect(Collectors.toList());
        WriteSheet writeSheet = EasyExcel.writerSheet(1, SHEET1_NAME)
                .head(head)
                .build();
        excelWriter.write(dataList, writeSheet);
    }


    @Override
    protected boolean exceptionNoticeFlag() {
        return false;
    }

    @Override
    protected void beforeQueryDataValidate(BaseExcelParam param) {

    }

    @Override
    protected void easyExcelExport(HttpServletResponse response, List<Object> dataList, List<List<String>> headList) throws IOException {

    }

    @Override
    public String fileName() {
        return "";
    }

}
