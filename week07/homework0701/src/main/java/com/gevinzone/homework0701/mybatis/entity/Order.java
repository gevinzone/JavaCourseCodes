package com.gevinzone.homework0701.mybatis.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Order {
    private long id;
    private long userId;
    private long businessId;
    private List<OrderItem> items;
    private BigDecimal amount;
    private Date createTime;
    private Date updateTime;
}
