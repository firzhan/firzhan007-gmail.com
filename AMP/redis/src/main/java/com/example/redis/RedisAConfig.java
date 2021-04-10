package com.example.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisAConfig {


    private JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory
                = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUseSsl(false);
        return jedisConnectionFactory;
    }

    @Bean("RedisAT")
    public RedisTemplate<String, Student> redisTemplate() {
        RedisTemplate<String, Student> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
