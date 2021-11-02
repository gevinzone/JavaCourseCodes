package com.gevinzone.homework0701.mybatis.mapper;

import com.gevinzone.homework0701.mybatis.entity.User;
import com.gevinzone.homework0701.mybatis.mapper.sql.UserSqlBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO `user` (username, `password`, salt, nickname, id_number, create_time, update_time)\n" +
            "VALUES (#{username}, #{password}, #{salt}, #{nickname}, #{idNumber}, #{createTime}, #{updateTime})")
    int insertUser(User user);

    @Select("SELECT * FROM `user` WHERE id=#{id}")
    User getUser(@Param("id") long id);

    @InsertProvider(type = UserSqlBuilder.class, method = "insertUsers")
    Integer insertUsers(@Param("users") List<User> users);
}
