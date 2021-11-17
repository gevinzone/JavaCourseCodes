package com.gevinzone.usbank.bo;

import com.gevinzone.remittancecontract.Const;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ReceiveInfo {
    private long userId;
    private BigDecimal amount;
    private long businessId;
    private Date updateTime;

    public static ReceiveInfo loadReceiveInfoFromAccountTransferDetail(AccountTransferDetail detail) {
        Date now = new Date();
        return ReceiveInfo.builder()
                .userId(Long.parseLong(detail.getFromId().substring(Const.usAccountPrefix.length())))
                .amount(detail.getAmountUs())
                .businessId(detail.getId())
                .updateTime(now).build();
    }
}
