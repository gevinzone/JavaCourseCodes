package com.gevinzone.homework0703.service;

import com.gevinzone.homework0703.entity.User;
import com.gevinzone.homework0703.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserMapper mapper;

    public User getUser() {
        return mapper.getUser("gevin");
    }

    public User getUser(String username) {
        return mapper.getUser(username);
    }

    public void insertUser(String username) {
        mapper.insertUser(createUser(username));
    }

    @Transactional
    public User insertUserInTransaction(String username) {
        mapper.insertUser(createUser(username));
        return mapper.getUser(username);
    }

    private User createUser(String username) {
        return User.builder()
                .username(username)
                .password("password")
                .idNumber("12345")
                .nickname("nickname")
                .salt("salt")
                .createTime(new Date())
                .updateTime(new Date()).build();
    }
}
