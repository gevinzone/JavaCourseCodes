package com.gevinzone.homework0701.mybatis;

import com.gevinzone.homework0701.mybatis.entity.User;
import com.gevinzone.homework0701.mybatis.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MyBatisBatchInsert {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    /*
        This does not work as expect
        @Autowired
        UserMapper userMapper;
     */

    public void batchInsertUser(int total, String prefix) {
        int batchSize = 50_000;
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (int i = 0; i < total; i+= batchSize) {
            int batch = Math.min(total - i, batchSize);
//            List<User> users = createUserList(i, batch, prefix, now);
//            batchInsertUser(sqlSession, userMapper, users);
            batchInsertUser(sqlSession, userMapper, prefix, now, i, batch);
        }
        sqlSession.close();
    }

    public void batchInsertUser2(int total, String prefix) {
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        batchInsertUser(sqlSession, userMapper, prefix, now, 0, total);
        sqlSession.close();
    }

    private void batchInsertUser(SqlSession sqlSession, UserMapper userMapper, List<User> userList) {
        for (User user : userList) {
            userMapper.insertUser(user);
        }
        sqlSession.commit();
    }

    private void batchInsertUser(SqlSession sqlSession, UserMapper userMapper, String prefix, Date date, int start, int size) {
        User user = User.builder().build();
        prefix = prefix == null ? "user" : prefix + "user";
        for (int i = 0; i < size; i++) {
            user.setUsername(prefix + (start + i));
            user.setPassword("password");
            user.setNickname("nickname");
            user.setSalt("salt");
            user.setCreateTime(date);
            user.setUpdateTime(date);
            userMapper.insertUser(user);
        }
        sqlSession.commit();
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
                    .createTime(date)
                    .updateTime(date)
                    .build());
        }
        return users;
    }
}
