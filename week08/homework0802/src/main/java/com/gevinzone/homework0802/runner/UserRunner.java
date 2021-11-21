package com.gevinzone.homework0802.runner;

import com.gevinzone.homework0802.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRunner implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        running();
    }

    private void running() {
        int total = 1000;
        String prefix = "M1-";
        userService.batchInsertUser(total, prefix);
//        userService.insertUser("test");
    }
}
