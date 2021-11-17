package com.gevinzone.remittancecontract.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Account {
    private long id;
    private String username;
    private BigDecimal balance;
    private BigDecimal frozen;
    private Date createTime;
    private Date updateTime;
}
