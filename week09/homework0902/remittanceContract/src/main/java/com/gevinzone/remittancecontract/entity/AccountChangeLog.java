package com.gevinzone.remittancecontract.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AccountChangeLog {
    private long id;
    private long businessId;
    private long userId;
    private BigDecimal amount;
    private int status;
    private Date createTime;
    private Date updateTime;
}
