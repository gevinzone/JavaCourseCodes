package com.gevinzone.homework0802.service;

import com.gevinzone.homework0802.entity.Order;
import com.gevinzone.homework0802.entity.OrderItem;
import com.gevinzone.homework0802.mapper.OrderMapper;
import com.gevinzone.homework0802.util.IdGenerator;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    IdGenerator idGenerator;

    @ShardingTransactionType(TransactionType.LOCAL)
    @Transactional
    public void insertOrder() {
        Order order = createOrder();

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        insertOrder(order, mapper);
        sqlSession.commit();
        sqlSession.close();

    }

    @ShardingTransactionType(TransactionType.LOCAL)
    @Transactional
    public void insertOrderWithException() {
        insertOrder();
        throw new RuntimeException("simulate exception");

    }

    public void insertOrdersBatch(int total) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        int batchSize = 20_000;
        for (int i = 0; i < total; i += batchSize) {
            int batch = Math.min(total - i, batchSize);
            List<Order> orders = createOrders(batch);
            insertOrders(orders, sqlSession, mapper);
        }
        sqlSession.close();
    }

    private void insertOrder(Order order, OrderMapper mapper) {
        for (OrderItem item : order.getItems()) {
            mapper.insertOrderItem(item);
        }
        mapper.insertOrder(order);
    }

    private Order createOrder() {
        long orderId = idGenerator.nextId();
        long userId = (long)(Math.random() * 100) + 1;

        Date now = new Date();
        List<OrderItem> items = Stream.of(OrderItem.builder().orderId(orderId).userId(userId).commodityId(1L).commodityName("goods")
                        .commodityPrice(new BigDecimal("23.30")).createTime(now).updateTime(now).build(),
                OrderItem.builder().orderId(orderId).userId(userId).commodityId(1L).commodityName("goods")
                        .commodityPrice(new BigDecimal("23.30")).createTime(now).updateTime(now).build()
        ).collect(Collectors.toList());

        return Order.builder().id(orderId).businessId(orderId).userId(userId).amount(new BigDecimal("50.02"))
                .items(items).createTime(now).updateTime(now).build();
    }

    private void insertOrders(List<Order> orders, SqlSession sqlSession, OrderMapper mapper) {
        insertOrdersOnly(orders, mapper);
        for (Order order: orders) {
            insertOrderItemsOnly(order.getItems(), mapper);
        }
        sqlSession.commit();
    }

    private void insertOrdersOnly(List<Order> orders, OrderMapper mapper) {
        for (Order order : orders) {
            mapper.insertOrder(order);
        }
    }

    private void insertOrderItemsOnly(List<OrderItem> orderItems, OrderMapper mapper) {
        for (OrderItem item : orderItems) {
            mapper.insertOrderItem(item);
        }
    }


    private List<Order> createOrders(int size) {
        List<Order> orders = new ArrayList<>(size * 2);
        for (int i = 0; i < size; i++) {
            orders.add(createOrder());
        }
        return orders;
    }
}
