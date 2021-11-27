package com.gevinzone.homework1102.mapper;

import com.gevinzone.homework1102.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User find(int id);

    @Select("select * from user")
    List<User> list();
}
