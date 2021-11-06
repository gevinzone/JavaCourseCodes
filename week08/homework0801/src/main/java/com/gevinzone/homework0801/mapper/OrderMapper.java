package com.gevinzone.homework0801.mapper;

import com.gevinzone.homework0801.entity.Order;
import com.gevinzone.homework0801.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `t_order` (id, user_id, amount, create_time, update_time)\n" +
            "VALUES(#{id}, #{userId}, #{amount}, #{createTime}, #{updateTime})")
    Integer insertOrder(Order order);

    @Insert("INSERT INTO t_order_item (order_id, user_id, commodity_id, commodity_name, commodity_price, create_time, update_time)\n" +
            "VALUES (#{orderId}, #{userId}, #{commodityId}, #{commodityName}, #{commodityPrice}, #{createTime}, #{updateTime})")
    Integer insertOrderItem(OrderItem orderItem);
}
