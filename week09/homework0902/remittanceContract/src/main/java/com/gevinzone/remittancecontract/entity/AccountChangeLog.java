package com.gevinzone.remittancecontract.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountChangeLog {
    private long id;
    private long businessId;
    private long relatedUserId;
    private BigDecimal inAmount;
    private int status;
    private Date createTime;
    private Date updateTime;
}
