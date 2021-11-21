package com.gevinzone.homework0802.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderItem {
    private long id;
    private long orderId;
    private long userId;
    private long commodityId;
    private String commodityName;
    private BigDecimal commodityPrice;
    private Date createTime;
    private Date updateTime;
}
