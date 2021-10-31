package com.gevinzone.homework0703;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gevinzone.homework0703.mapper")
@SpringBootApplication
public class Homework0703Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework0703Application.class, args);
    }

}
