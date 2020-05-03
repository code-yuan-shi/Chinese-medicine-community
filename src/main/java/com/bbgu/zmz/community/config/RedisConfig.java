package com.bbgu.zmz.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    public JedisPool jedisPoolFactory() {
        System.out.println("JedisPool注入开始...");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(1500);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(false);
        // 是否启用pool的jmx管理功能, 默认tru
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "101.200.47.40", 6379,30000,"123456",0);
        System.out.println("JedisPool注入成功...");
        return jedisPool;
    }
}
