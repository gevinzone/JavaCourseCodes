package com.gevinzone.homework1102;

import com.gevinzone.homework1102.entity.User;
import com.gevinzone.homework1102.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class Homework1102ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testMapper() {
        User user = userMapper.find(1);
        log.info(user.toString());

        List<User> users = userMapper.list();
        log.info(users.toString());
    }

}
