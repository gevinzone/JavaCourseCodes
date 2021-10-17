package com.gevinzone.startertest;

import com.gevinzone.homework0501.service.IMeetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StarterTestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StarterTestApplication.class, args);
        TestService testService = context.getBean(TestService.class);
        testService.test();
        testService.test2();
    }

}
