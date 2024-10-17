package com.example.boost.circuitbreaker;

public class Stat {

    private Long total;

    private Long success;

    public Stat(Long total, Long success) {
        this.total = total;
        this.success = success;
    }

    public Long getTotal() {
        return total;
    }

    public Long getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "total=" + total +
                ", success=" + success +
                '}';
    }
}
