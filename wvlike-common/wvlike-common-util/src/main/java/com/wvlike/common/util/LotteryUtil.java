package com.wvlike.common.util;


import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.ToDoubleFunction;

public class LotteryUtil {

    /**
     * 抽奖方法
     *
     * @param prizeList   奖项池
     * @param probability 奖项概率转换函数
     * @param <T>         奖项类型
     * @return 抽中的奖品
     */
    public static <T> T lottery(List<T> prizeList, ToDoubleFunction<T> probability) {
        if (CollectionUtils.isEmpty(prizeList)) {
            return null;
        }
        //所有奖项概率值之和
        double total = prizeList.stream().mapToDouble(probability).sum();
        if (total <= 0) {
            return null;
        }
        //产生随机数 0 < random < 1
        double random = RandomUtils.nextDouble(0, 1);
        double start, end = 0;
        for (T prize : prizeList) {
            start = end;
            end = end + (probability.applyAsDouble(prize) / total);
            if (random >= start && random < end) {
                return prize;
            }
        }
        return null;
    }

}