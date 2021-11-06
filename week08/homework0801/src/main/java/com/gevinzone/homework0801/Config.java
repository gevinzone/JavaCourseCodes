package com.gevinzone.homework0801;

import com.gevinzone.homework0801.utils.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean("idGenerator")
    public IdGenerator idGenerator() {
        return new IdGenerator(1, 1);
    }
}
