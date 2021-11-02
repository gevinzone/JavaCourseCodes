package com.gevinzone.homework0703;

import com.gevinzone.homework0703.entity.User;
import com.gevinzone.homework0703.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmdRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User user = userService.getUser();
        log.info(user.toString());
        String username = "newUser";
        userService.insertUser(username);
        username = "newUser2";
        log.info(userService.insertUserInTransaction(username).toString());
    }
}
