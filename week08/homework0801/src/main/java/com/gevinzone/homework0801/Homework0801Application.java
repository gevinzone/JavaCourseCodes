package com.gevinzone.homework0801;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gevinzone.homework0801.mapper")
public class Homework0801Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework0801Application.class, args);
    }

}
