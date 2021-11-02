package com.gevinzone.homework0701.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowflakeIdGenerator {
    @Getter
    private final long workerId;
    @Getter
    private final long dataCenterId;
    private long sequence = 0L;

    private final int workerIdBits = 5;
    private final int datacenterIdBits = 5;
    private final int sequenceBits = 15;

    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (dataCenterId > maxDatacenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        log.info("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, worker id {}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    //下一个ID生成算法
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        lastTimestamp = calculateCurrentSequence(timestamp);
        return calculateId(timestamp);
    }

    /**
     * 计算序列号，当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号从0开始。
     *
     * @param timestamp 计算前的时间戳
     * @return 计算后的时间戳
     */
    private long calculateCurrentSequence(long timestamp) {
        if (lastTimestamp != timestamp) {
            sequence = 0;
            return timestamp;
        }
        sequence++;
        long sequenceMask = ~(-1L << sequenceBits);
        if ((sequence & sequenceMask) == 0) {
            return calculateCurrentSequence(tilNextMillis(lastTimestamp));
        }

        return timestamp;
    }

    private long calculateId(long timestamp) {
        /*
            初始时间戳为1288834974657L（2010年11月04日01:42:54），固定值，正好在2^41内
            工作id需要左移的位数，12位
            数据id需要左移位数 12+5=17位
         */
        long epoch = 1288834974657L;
        long datacenterIdShift = sequenceBits + workerIdBits;
        return ((timestamp - epoch) << timestampLeftShift) |
                (dataCenterId << datacenterIdShift) |
                (workerId << sequenceBits) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
//            try {
//                TimeUnit.MICROSECONDS.sleep(10);
//            } catch (InterruptedException exception) {
//                log.error(exception.getMessage());
//            }
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
