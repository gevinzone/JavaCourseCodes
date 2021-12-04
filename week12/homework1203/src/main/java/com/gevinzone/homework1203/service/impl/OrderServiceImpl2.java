package com.gevinzone.homework1203.service.impl;

import com.gevinzone.homework1203.entity.Order;
import com.gevinzone.homework1203.mapper.OrderMapper;
import com.gevinzone.homework1203.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


@Service("orderService2")
public class OrderServiceImpl2 implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order processOrder() {
        return null;
    }

    @JmsListener(destination = "gevin.orderQueue")
    public void processOrder(Order order) {
        orderMapper.updateOrderStatus(order.getId());
    }
}
