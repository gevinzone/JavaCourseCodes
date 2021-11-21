package com.gevinzone.homework0802;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gevinzone.homework0802.mapper")
@SpringBootApplication
public class Homework0802Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework0802Application.class, args);
    }

}
