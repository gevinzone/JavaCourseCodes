package com.gevinzone.homework1101;

import com.gevinzone.homework1101.redistemplate.TemplateLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class AppConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Bean(name = "hashMapRestTemplate")
//    public RedisTemplate<String, HashMap<String, String>> hashMapRestTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, HashMap<String, String>> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        return template;
//    }

//    @Bean(name = "templateLock")
//    public TemplateLock templateLock() {
//        return new TemplateLock();
//    }


}
