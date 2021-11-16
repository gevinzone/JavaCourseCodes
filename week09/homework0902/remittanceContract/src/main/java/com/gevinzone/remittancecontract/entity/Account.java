package com.gevinzone.remittancecontract.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Account {
    private long userId;
    private BigDecimal balance;
    private BigDecimal frozen;
    private Date createTime;
    private Date updateTime;
}
