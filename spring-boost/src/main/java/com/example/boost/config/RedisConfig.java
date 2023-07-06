package com.example.boost.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Value("${redis.host:disable}")
    private String host;
    @Value("${redis.port:0}")
    private int port;
    @Value("${redis.database:0}")
    private int database;
    @Bean
    public RedisUtil getRedisUtil() {
        if (host.equals("disable")) return null;
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initPool(host,port,database);
        return redisUtil;
    }
}
