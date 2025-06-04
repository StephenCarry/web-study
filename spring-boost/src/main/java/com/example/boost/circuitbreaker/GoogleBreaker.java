package com.example.boost.circuitbreaker;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class GoogleBreaker {
    /**
     * 熔断敏感度，值越小越敏感
     */
    private double k = 1.5;

    /**
     * 接口信息滑动窗口
     */
    private Map<String, RollingWindow> windowMap = new ConcurrentHashMap<>(8);

    /**
     * 随机数产生器
     */
    private Random random = new Random();

    public void doRequest(String name, ProceedingJoinPoint point) {
        long startTime = System.currentTimeMillis();;
        boolean accept = true;
        try {
            accept = this.accept(name);
            if (!accept) {
                throw new RuntimeException("熔断");
            }

            point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            if (accept) {
                // 判断本次是上报成功还是失败
                long costTime = endTime - startTime;
                if (costTime < 1) {
                    costTime = 1;
                }
                RollingWindow rw = this.rollingWindow(name);
                Stat stat = rw.reduce();
                long total = stat.getTotal();
                int expNum = (int) ((60 * 1000 / costTime) * 3 * 0.8);
                // 测试打印
                System.out.println("costTime=["+costTime+"], total=["+total+"], expNum=["+expNum+"]");
                if (total < expNum) {
                    this.success(name);
                } else {
                    this.failed(name);
                }
            }
        }

    }

    public void success(String name) {
        this.rollingWindow(name).add(1);
    }

    public void failed(String name) {
        this.rollingWindow(name).add(0);
    }

    public boolean accept(String name) {
        // 默认通过
        boolean accept = true;
        // 获取接口近期统计数据
        RollingWindow rw = this.rollingWindow(name);
        Stat stat = rw.reduce();
        // 计算动态熔断概率
        Long total = stat.getTotal();
        Long success = stat.getSuccess();
        double weightAccepts = k * success;
        double dorpRatio = Math.max(0, (total - weightAccepts) / (total + 1));
        // 概率为0则直接通过
        if (dorpRatio <= 0) return accept;
        // 产生随机数与熔断概率比较
        double x = random.nextDouble();
        if (x < dorpRatio) {
            accept = false;
        }
        return accept;
    }

    private RollingWindow rollingWindow(String name) {
        RollingWindow rw = windowMap.get(name);
        if (rw == null) {
            rw = new RollingWindow(60 * 1000, 60, false);
            windowMap.put(name, rw);
        }
        return rw;
    }
}
