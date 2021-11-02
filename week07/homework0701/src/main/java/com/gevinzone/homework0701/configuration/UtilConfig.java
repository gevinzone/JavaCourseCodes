package com.gevinzone.homework0701.configuration;

import com.gevinzone.homework0701.utils.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean("idGenerator")
    public SnowflakeIdGenerator idGenerator() {
        return new SnowflakeIdGenerator(1, 1);
    }
}
