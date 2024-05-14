package com.wvlike.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

/**
 * @Date: 2023/7/11
 * @Author: tuxinwen
 * @Description:
 */
public class OtherTest {

    @Test
    public void test() {
        LocalDate localDate = LocalDate.parse("2023-07-12");
        System.out.println(localDate);
        System.out.println(localDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
        LocalDate thisQuarterStart = LocalDate.parse("2023-07-12")
                .minusMonths((localDate.getMonth().getValue() - 1) % 3)
                .with(ChronoField.DAY_OF_MONTH, 1L);
        int intervalDays = localDate.getDayOfYear() - thisQuarterStart.getDayOfYear();
        // 设置成上季开始
        localDate = localDate.minusMonths((localDate.getMonth().getValue() - 1) % 3 + 3)
                .with(ChronoField.DAY_OF_MONTH, 1L)
                .plusDays(intervalDays);
        // 判断设置是否 = 当前季度
        if (localDate.getMonth() == thisQuarterStart.getMonth()) {
            localDate = thisQuarterStart.minusDays(1L);
        }
        System.out.println(localDate);
    }

    @Test
    public void test001() {
        List<?> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
        List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
        List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
        List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
        List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

        List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        BigExcelWriter writer = ExcelUtil.getBigWriter("C:/Users/kingdee/Desktop/xxx.xlsx");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        // 关闭writer，释放内存
        writer.close();

    }




}
