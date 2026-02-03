package com.wvlike.common.core.snowflake;

import lombok.Getter;
import lombok.Setter;

/**
 * package com.wvlike.testDmo.myselfFramework.utils;
 *
 * @auther txw
 * @create 2021-01-16  20:12
 * @description：
 */
public class SnowFlakeIdGenerator {

    @Getter
    @Setter
    private long twepoch = 1483200000000L;

    private static final long CUSTOM_ID_BITS = 12L;

    /**
     * 业务id所占的位数
     */
    private final long customIdBits;

    private static final long SEQ_BITS = 12L;

    /**
     * 序列在id中占的数
     */
    private final long sequenceBits;

    /**
     * 毫秒内序列(0~4096)
     */
    private long sequence = 0;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = 0;

    private final long customId;

    public SnowFlakeIdGenerator(long workerId) {
        this(workerId, CUSTOM_ID_BITS, SEQ_BITS);
    }

    public SnowFlakeIdGenerator(long workerId, long customIdBits, long sequenceBits) {
        this.customIdBits = customIdBits;
        this.sequenceBits = sequenceBits;
        this.customId = workerId & (~(-1L << customIdBits));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp == lastTimestamp) {
            /**
             * 生成序列的掩码
             */
            sequence = (sequence - 1) & (~(-1L << sequenceBits));
            if (sequence == 0) {
                timestamp = tilNextMillis();
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        /**
         * 时间戳向左移24位(12 + 12)
         * 业务ID向左移8位
         */
        return ((timestamp - twepoch) << (sequenceBits + customIdBits))
                | (customId << sequenceBits)
                | sequence;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

}
