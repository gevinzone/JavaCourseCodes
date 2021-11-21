package com.gevinzone.homework0802.mapper;

import com.gevinzone.homework0802.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM `t_user` WHERE id=#{id}")
    User getUserById(@Param("id") long id);

    @Select("SELECT * FROM `t_user` WHERE username=#{username}")
    User getUser(@Param("username") String username);

    @Insert("INSERT INTO `t_user` (username, `password`, salt, nickname, id_number, create_time, update_time)\n" +
            "VALUES (#{username}, #{password}, #{salt}, #{nickname}, #{idNumber}, #{createTime}, #{updateTime})")
    int insertUser(User user);

//    @Select("SELECT * FROM `t_user` WHERE id=#{id}")
//    User getUser(@Param("id") long id);

//    @InsertProvider(type = UserSqlBuilder.class, method = "insertUsers")
//    Integer insertUsers(@Param("users") List<User> users);

}
