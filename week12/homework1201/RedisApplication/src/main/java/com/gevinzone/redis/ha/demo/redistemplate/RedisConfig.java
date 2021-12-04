package com.gevinzone.redis.ha.demo.redistemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    @Autowired
    RedisProperties redisProperties;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(sentinelConfiguration());
//        factory.setDatabase(redisProperties.getDatabase());
//        return factory;
//    }
//
//    @Bean
//    public RedisSentinelConfiguration sentinelConfiguration() {
//        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
//        configuration.master(redisProperties.getSentinel().getMaster());
//        for (String node : redisProperties.getSentinel().getNodes()) {
//            configuration.sentinel(node.split(":")[0], Integer.parseInt(node.split(":")[1])) ;
//        }
//        return configuration;
//    }
    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
