package com.gevinzone.homework0801.service;

import com.gevinzone.homework0801.entity.User;
import com.gevinzone.homework0801.mapper.UserMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public void batchInsertUser(int total, String prefix) {
        int batchSize = 50_000;
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (int i = 0; i < total; i+= batchSize) {
            int batch = Math.min(total - i, batchSize);
            List<User> users = createUserList(i, batch, prefix, now);
            batchInsertUser(sqlSession, userMapper, users);
        }
        sqlSession.close();
    }

    private List<User> createUserList(int start, int size, String prefix, Date date) {
        List<User> users = new ArrayList<>(size * 2);
        prefix = prefix == null ? "user" : prefix + "user";
        for (int i = 0; i < size; i++) {
            users.add(User.builder()
                    .username(prefix + (start + i))
                    .password("password")
                    .nickname("nickname")
                    .salt("salt")
                    .idNumber("479894105789734")
                    .createTime(date)
                    .updateTime(date)
                    .build());
        }
        return users;
    }

    private void batchInsertUser(SqlSession sqlSession, UserMapper userMapper, List<User> userList) {
        for (User user : userList) {
            userMapper.insertUser(user);
        }
        sqlSession.commit();
    }
}
