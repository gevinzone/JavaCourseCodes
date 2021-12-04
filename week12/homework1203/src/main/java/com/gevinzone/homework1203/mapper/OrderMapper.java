package com.gevinzone.homework1203.mapper;

import com.gevinzone.homework1203.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Select("SELECT id, `status`, create_time, update_time\n" +
            "FROM `order`\n" +
            "WHERE id = #{id};")
    Order getOrder(int id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `order` (`status`, create_time, update_time)\n" +
            "VALUES (#{status}, #{createTime}, #{updateTime});")
    Integer createOrder(Order order);


    @Update("UPDATE `order` SET `status` = 1, update_time = NOW()\n" +
            "WHERE id = #{id};")
    Integer updateOrderStatus(int id);
}
