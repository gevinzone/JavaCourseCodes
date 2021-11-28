package com.gevinzone.homework1102.controller;

import com.gevinzone.homework1102.entity.User;
import com.gevinzone.homework1102.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user/detail")
    User find(Integer id) {
        return userService.find(id);
    }

    @RequestMapping("/user/list")
    List<User> list() {
        return userService.list();
    }

    @PutMapping("/user/detail")
    User updateUserIgnoreCache(@RequestBody User user, @RequestParam(required = false, defaultValue = "0") boolean ignore) {
        return ignore ? userService.updateIgnoreCache(user, user.getId()) : userService.update(user, user.getId());
    }

    @DeleteMapping("/user/list")
    void clearAllCache() {
        userService.clearAllCache();
    }

    @DeleteMapping("/user/detail")
    void clearCache(int id) {
        userService.clearCache(id);
    }
}
