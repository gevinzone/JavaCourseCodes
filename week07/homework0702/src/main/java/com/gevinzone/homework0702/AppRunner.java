package com.gevinzone.homework0702;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    UserCrud userCrud;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Create user1: \n");
        userCrud.createUser();
        System.out.println("Create user2: \n");
        userCrud.createUser2();
    }
}
