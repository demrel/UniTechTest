package com.UniTech.UniTechTest.config;

import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.math.BigDecimal;

@Configuration
public class RedisConfiguration {



    @Bean
    public RedisTemplate<String, BigDecimal> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BigDecimal> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }

}
