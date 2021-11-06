package com.gevinzone.homework0801.runner;

import com.gevinzone.homework0801.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRunner implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        running();
    }

    private void running() {
        int total = 100000;
        String prefix = "M1-";
        userService.batchInsertUser(total, prefix);
    }
}
