package com.gevinzone.homework0802.config;

import com.gevinzone.homework0802.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DbConfig {
    @Bean("idGenerator")
    public IdGenerator idGenerator() {
        return new IdGenerator(1, 1);
    }
}
