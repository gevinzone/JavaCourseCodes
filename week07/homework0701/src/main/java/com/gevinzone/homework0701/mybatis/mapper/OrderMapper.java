package com.gevinzone.homework0701.mybatis.mapper;

import com.gevinzone.homework0701.mybatis.entity.Order;
import com.gevinzone.homework0701.mybatis.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `order` (id, user_id, business_id, amount, create_time, update_time)\n" +
            "VALUES(#{id}, #{userId}, #{businessId}, #{amount}, #{createTime}, #{updateTime})")
    Integer insertOrder(Order order);

    @Insert("INSERT INTO order_item (id, order_id, commodity_id, commodity_name, commodity_price, create_time, update_time)\n" +
            "VALUES (#{id}, #{orderId}, #{commodityId}, #{commodityName}, #{commodityPrice}, #{createTime}, #{updateTime})")
    Integer insertOrderItem(OrderItem orderItem);
}
