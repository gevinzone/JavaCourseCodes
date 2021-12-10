package io.kimmking.javacourse.kafkademo.service;

import io.kimmking.javacourse.kafkademo.entity.Order;

public interface OrderService {
    void sendOrder(Order order);
    void sendOrderDefault(Order order);
}
