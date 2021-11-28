package com.gevinzone.homework1102.service;

import com.gevinzone.homework1102.entity.User;

import java.util.List;

public interface UserService {

    User find(int id);

    List<User> list();

    User updateIgnoreCache(User user, int id);

    User update(User user, int id);

    void clearAllCache();

    void clearCache(int id);
}
