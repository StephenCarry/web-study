package com.example.boost.circuitbreaker;

import java.util.concurrent.atomic.AtomicLong;

public class Bucket {

    private AtomicLong sum = new AtomicLong(0);

    private AtomicLong count = new AtomicLong(0);

    public Long getValue() {
        return sum.get();
    }

    public Long getCount() {
        return count.get();
    }

    public void add(long value) {
        sum.addAndGet(value);
        count.incrementAndGet();
    }

    public void reset() {
        sum = new AtomicLong(0);
        count = new AtomicLong(0);
    }
}
