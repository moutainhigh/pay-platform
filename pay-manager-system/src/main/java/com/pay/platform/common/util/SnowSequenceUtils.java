package com.pay.platform.common.util;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;


/**
 *
 * 生成19位唯一值
 * 雪花算法
 * ps：没法保证绝对的唯一，建议数据库要做唯一性约素
 * 在10线程1000并发下，能保证99.88%可用性
 *
 */
public class SnowSequenceUtils {
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;


    static SnowSequenceUtils idWorker = new SnowSequenceUtils(0, 0);

    /**
     *
     * @return
     */
    public static String getSequence(){
        return String.valueOf(idWorker.nextId());
    }


    private SnowSequenceUtils(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        SnowSequenceUtils idWorker = new SnowSequenceUtils(0, 0);
//        for (int i = 0; i < 100000; i++) {
//            long id = idWorker.nextId();
//            System.out.println(id);
//        }
        Set<Long> set=new HashSet<>();
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        long id = idWorker.nextId();
                        set.add(id);
                        System.out.println(id);
                    }
                }
            }).start();
        }
        sleep(5000);
        System.out.println(set.size());
    }
}
