package com.gevinzone.homework1102.service;

import com.gevinzone.homework1102.entity.User;

import java.util.List;

public interface UserService {

    User find(int id);

    List<User> list();

}
