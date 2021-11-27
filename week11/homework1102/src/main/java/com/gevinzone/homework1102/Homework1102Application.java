package com.gevinzone.homework1102;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.gevinzone.homework1102")
@MapperScan("com.gevinzone.homework1102.mapper")
@EnableCaching
public class Homework1102Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework1102Application.class, args);
    }

}
