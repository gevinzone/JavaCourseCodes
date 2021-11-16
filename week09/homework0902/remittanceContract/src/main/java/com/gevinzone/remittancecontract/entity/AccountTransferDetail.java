package com.gevinzone.remittancecontract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferDetail implements Serializable {
    private long id;
    private String fromId;
    private String toId;
    private BigDecimal amountUs;
    private BigDecimal amountCny;
    private Date createTime;
    private Date updateTime;
    private int status;
}
