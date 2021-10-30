package com.gevinzone.homework0702;

import com.gevinzone.homework0702.annotation.CurDataSource;
import com.gevinzone.homework0702.datasource.DataSourceEnum;
import com.gevinzone.homework0702.entity.User;
import com.gevinzone.homework0702.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserCrud {
    @Autowired
    private UserMapper mapper;

    @CurDataSource
    public void createUser() {
        mapper.insertUser(User.builder()
                .username(String.valueOf(System.currentTimeMillis()))
                .password("password")
                .nickname("nickname")
                .salt("salt")
                .idNumber("479894105789734")
                .createTime(new Date())
                .updateTime(new Date()).build());
    }
    @CurDataSource(value = DataSourceEnum.SLAVE)
    public void createUser2() {
        mapper.insertUser(User.builder()
                .username(String.valueOf(System.currentTimeMillis()))
                .password("password")
                .nickname("nickname")
                .salt("salt")
                .idNumber("479894105789734")
                .createTime(new Date())
                .updateTime(new Date()).build());
    }
}
