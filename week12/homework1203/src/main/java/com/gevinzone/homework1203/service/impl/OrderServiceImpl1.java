package com.gevinzone.homework1203.service.impl;

import com.gevinzone.homework1203.entity.Order;
import com.gevinzone.homework1203.mapper.OrderMapper;
import com.gevinzone.homework1203.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

@Service("orderService1")
public class OrderServiceImpl1 implements OrderService {
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    Destination orderQueueDestination;
    @Autowired
    OrderMapper orderMapper;


    @Override
    public Order createOrder(Order order) {
        orderMapper.createOrder(order);
        jmsTemplate.convertAndSend(orderQueueDestination, order);
        return order;
    }

    @Override
    public Order processOrder() {
        Order order = (Order) jmsTemplate.receiveAndConvert(orderQueueDestination);
        if (order == null) {
            return null;
        }
        order.setStatus(1);
        orderMapper.updateOrderStatus(order.getId());
        return order;
    }
}
