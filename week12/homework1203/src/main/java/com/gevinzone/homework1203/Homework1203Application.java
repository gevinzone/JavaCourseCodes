package com.gevinzone.homework1203;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gevinzone.homework1203.mapper")
@SpringBootApplication
public class Homework1203Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework1203Application.class, args);
    }

}
