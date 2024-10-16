package com.example.boost.circuitbreaker;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RollingWindow {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Date() + ": 当前时间窗口启动");
        RollingWindow rollingWindow = new RollingWindow(60 * 1000, 60, false);
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println(new Date() + ": 当前总写入: ["+i+"]");
            }
            rollingWindow.add(1);
            Thread.sleep(200);
        }
        rollingWindow.reduce();
    }

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * bucket数组
     */
    private final Bucket[] buckets;

    /**
     * 当前滑动窗口的bucket总数
     */
    private final int size;

    /**
     * 当前时间窗口单个bucket大小
     */
    private final long interval;

    /**
     * 统计值时是否统计当前时间戳位于的bucket
     */
    private final boolean ignoreCurr;

    /**
     * 当前滑动窗口的bucket开始位置
     */
    private int offset;

    /**
     * 当前滑动窗口最后一个统计数据的时间戳
     */
    private long lastTime;

    public RollingWindow(long windonMillis, int size, boolean ignoreCurr) {
        this.size = size;
        this.interval = windonMillis / size;
        this.buckets = new Bucket[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new Bucket();
        }
        this.ignoreCurr = ignoreCurr;
        this.offset = 0;
        this.lastTime = TimeUtil.currentTime();
    }

    /**
     * 汇总窗口数据
     */
    public void reduce() {
        try {
            lock.readLock().lock();
            // 汇总数据前判断哪些桶无需统计
            int diff;
            int span = this.span();
            // 当前时间截止前未过期的桶个数
            if (span == 0 && ignoreCurr) {
                diff = size - 1;
            } else {
                diff = size - span;
            }
            // 如果存在未过期的桶则汇总数据
            if (diff > 0) {
                int start = (offset + span + 1) % size;
                this.reduceBucket(start, diff);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 往当前bucket写入数据
     */
    public void add(long value) {
        try {
            lock.writeLock().lock();
            this.updateOffset();
            this.addBucket(this.offset, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 通过更新offset和lastTime实现窗口滑动
     */
    private void updateOffset() {
        // 获取当前经过的span个数
        int span = this.span();
        if (span <= 0) {
            return;
        }
        // 清理过期桶
        int offset = this.offset;
        for (int i = 0; i < span; i++) {
            this.resetBucket((offset + i + 1) % size);
        }
        // 更新当前offset
        this.offset = (offset + span) % size;
        // 更新操作时间
        long now = TimeUtil.currentTime();
        this.lastTime = now - ((now - lastTime) % interval);
    }

    /**
     * 计算当前时间略过了多少个窗口
     * 假如windowMillis=60000, size=60
     * last=1729082511397, now=1729082522397
     * offset=(1729082522397-1729082511397)/1000=11
     * 11 < 60
     * @return nowTime span
     */
    private int span() {
        long now = TimeUtil.currentTime();
        int offset = (int) ((now - lastTime) / interval);
        if (offset >= 0 && offset < this.size) {
            return offset;
        }
        return size;
    }

    private void reduceBucket(int start, int count) {
        long success = 0;
        long total = 0;
        for (int i = 0; i < count; i++) {
            Bucket bucket = buckets[(start + i) % size];
            success+=bucket.getValue();
            total+=bucket.getValue();
        }
        // 测试打印
        System.out.println("count="+success);
        System.out.println("sum="+total);
    }

    private void addBucket(int offset, long value) {
        buckets[offset%size].add(value);
    }

    private void resetBucket(int offset) {
        buckets[offset%size].reset();
    }

}
