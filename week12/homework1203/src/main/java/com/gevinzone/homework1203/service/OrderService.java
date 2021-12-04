package com.gevinzone.homework1203.service;

import com.gevinzone.homework1203.entity.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order processOrder();
}
