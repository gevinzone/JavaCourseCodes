package com.gevinzone.cnbank.bo;

import com.gevinzone.remittancecontract.Const;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class PayInfo {
    private long userId;
    private BigDecimal amount;
    @Setter
    private long businessId;
    private Date updateTime;

    public static PayInfo loadPayInfoFromAccountTransferDetail(AccountTransferDetail detail) {
        Date now = new Date();
        return PayInfo.builder()
                .userId(Long.parseLong(detail.getFromId().substring(Const.cnAccountPrefix.length())))
                .amount(detail.getAmountCny())
                .businessId(detail.getId())
                .updateTime(now).build();
    }
}
