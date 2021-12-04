package com.gevinzone.homework1203;

import com.gevinzone.homework1203.entity.Order;
import com.gevinzone.homework1203.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j
@MapperScan("com.gevinzone.homework1203.mapper")
@SpringBootTest
class Homework1203ApplicationTests {
    @Autowired
    OrderMapper mapper;

    @Test
    void contextLoads() {
    }

    @Test
    void createOrder() {
        Date now = new Date();
        Order order = Order.builder().status(0).createTime(now).updateTime(now).build();
        mapper.createOrder(order);
        log.info(order.toString());
    }

    @Test
    void updateOrderStatus() {
        int id = 1;
        Assertions.assertEquals(mapper.updateOrderStatus(id), 1);

    }

}
