package com.gevinzone.homework0702.mapper;

import com.gevinzone.homework0702.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO `user` (username, `password`, salt, nickname, id_number, create_time, update_time)\n" +
            "VALUES (#{username}, #{password}, #{salt}, #{nickname}, #{idNumber}, #{createTime}, #{updateTime})")
    int insertUser(User user);

    @Select("SELECT * FROM `user` WHERE id=#{id}")
    User getUser(@Param("id") long id);

}
