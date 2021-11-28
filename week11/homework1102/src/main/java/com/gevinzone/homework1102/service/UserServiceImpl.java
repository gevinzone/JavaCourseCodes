package com.gevinzone.homework1102.service;

import com.gevinzone.homework1102.entity.User;
import com.gevinzone.homework1102.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper; //DAO  // Repository

    // 开启spring cache
    @Cacheable (key="#id",value="userCache")
//    @Cacheable (value="userCache")
    public User find(int id) {
        System.out.println(" ==> find " + id);
        return userMapper.find(id);
    }

    // 开启spring cache
    @Cacheable(key="methodName",value="userCache")
//    @Cacheable(value="userCache")
    public List<User> list(){
        return userMapper.list();
    }

    @Override
    public User updateIgnoreCache(User user, int id) {
        userMapper.update(user);
        return user;
    }

    @Override
    @CachePut(key="#id", value = "userCache")
//    @CachePut(value = "userCache")
    public User update(User user, int id) {
        userMapper.update(user);
        return user;
    }

    @Override
    @CacheEvict(value = "userCache", allEntries = true)
    public void clearAllCache() {
        System.out.println("clear all cache");
    }

    @Override
    @CacheEvict(value = "userCache", key = "#id")
    public void clearCache(int id) {
        User user = find(id);
        System.out.println("clear cache for:" + user);
    }
}
