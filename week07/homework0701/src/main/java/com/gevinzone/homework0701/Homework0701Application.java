package com.gevinzone.homework0701;

import com.gevinzone.homework0701.jdbc.Basic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Homework0701Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Homework0701Application.class, args);

        Basic basic = context.getBean(Basic.class);
        basic.insertUser();
    }

}
