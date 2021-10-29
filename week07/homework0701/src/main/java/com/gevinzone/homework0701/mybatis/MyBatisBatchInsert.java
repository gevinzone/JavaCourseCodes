package com.gevinzone.homework0701.mybatis;

import com.gevinzone.homework0701.mybatis.entity.Order;
import com.gevinzone.homework0701.mybatis.entity.OrderItem;
import com.gevinzone.homework0701.mybatis.entity.User;
import com.gevinzone.homework0701.mybatis.mapper.OrderMapper;
import com.gevinzone.homework0701.mybatis.mapper.UserMapper;
import com.gevinzone.homework0701.utils.SnowflakeIdGenerator;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MyBatisBatchInsert {
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SnowflakeIdGenerator idGenerator;

    /*
        This does not work as expect for batch query,
        but it can work with multiple value insertion
     */
    @Autowired
    UserMapper defaultUserMapper;

    public void batchInsertUser(int total, String prefix) {
        int batchSize = 50_000;
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (int i = 0; i < total; i+= batchSize) {
            int batch = Math.min(total - i, batchSize);
//            List<User> users = createUserList(i, batch, prefix, now);
//            batchInsertUser(sqlSession, userMapper, users);
            batchInsertUser(sqlSession, userMapper, prefix, now, i, batch);
        }
        sqlSession.close();
    }

    public void batchInsertUser2(int total, String prefix) {
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        batchInsertUser(sqlSession, userMapper, prefix, now, 0, total);
        sqlSession.close();
    }

    public void insertMultiUsers(int total, String prefix) {
        int batchSize = 50_000;
        Date now = new Date();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        prefix = prefix == null ? "user" : prefix + "user";
        for (int i = 0; i < total; i += batchSize) {
            int batch = Math.min(total - i, batchSize);
            List<User> users = createUserList(i, batch, prefix, now);
            userMapper.insertUsers(users);
            // it works, too
            // defaultUserMapper.insertUsers(users);
        }
    }

    private void batchInsertUser(SqlSession sqlSession, UserMapper userMapper, List<User> userList) {
        for (User user : userList) {
            userMapper.insertUser(user);
        }
        sqlSession.commit();
    }

    private void batchInsertUser(SqlSession sqlSession, UserMapper userMapper, String prefix, Date date, int start, int size) {
        User user = User.builder().build();
        prefix = prefix == null ? "user" : prefix + "user";
        for (int i = 0; i < size; i++) {
            user.setUsername(prefix + (start + i));
            user.setPassword("password");
            user.setNickname("nickname");
            user.setSalt("salt");
            user.setIdNumber("479894105789734");
            user.setCreateTime(date);
            user.setUpdateTime(date);
            userMapper.insertUser(user);
        }
        sqlSession.commit();
    }

    private List<User> createUserList(int start, int size, String prefix, Date date) {
        List<User> users = new ArrayList<>(size * 2);
        prefix = prefix == null ? "user" : prefix + "user";
        for (int i = 0; i < size; i++) {
            users.add(User.builder()
                    .username(prefix + (start + i))
                    .password("password")
                    .nickname("nickname")
                    .salt("salt")
                    .idNumber("479894105789734")
                    .createTime(date)
                    .updateTime(date)
                    .build());
        }
        return users;
    }

    @Transactional
    public void insertOrder() {
        Order order = createOrder();

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        insertOrder(order, mapper);
        sqlSession.commit();
        sqlSession.close();

    }

    private void insertOrder(Order order, OrderMapper mapper) {
        for (OrderItem item : order.getItems()) {
            mapper.insertOrderItem(item);
        }
        mapper.insertOrder(order);
    }


    public void insertOrdersBatch(int total) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        int batchSize = 20_000;
        for (int i = 0; i < total; i += batchSize) {
            int batch = Math.min(total - i, batchSize);
            List<Order> orders = createOrders(batch);
            insertOrders2(orders, sqlSession, mapper);
        }
        sqlSession.close();
    }

    private void insertOrders(List<Order> orders, SqlSession sqlSession, OrderMapper mapper) {
        for (Order order : orders) {
            insertOrder(order, mapper);
        }
        sqlSession.commit();
    }

    private void insertOrders2(List<Order> orders, SqlSession sqlSession, OrderMapper mapper) {
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

    private Order createOrder() {
        long orderId = idGenerator.nextId();

        Date now = new Date();
        List<OrderItem> items = Stream.of(OrderItem.builder().orderId(orderId).commodityId(1L).commodityName("goods")
                        .commodityPrice(new BigDecimal("23.30")).createTime(now).updateTime(now).build(),
                OrderItem.builder().orderId(orderId).commodityId(1L).commodityName("goods")
                        .commodityPrice(new BigDecimal("23.30")).createTime(now).updateTime(now).build()
        ).collect(Collectors.toList());

        return Order.builder().id(orderId).businessId(orderId).userId(1L).amount(new BigDecimal("50.02"))
                .items(items).createTime(now).updateTime(now).build();
    }

    private List<Order> createOrders(int size) {
        List<Order> orders = new ArrayList<>(size * 2);
        for (int i = 0; i < size; i++) {
            orders.add(createOrder());
        }
        return orders;
    }
}
