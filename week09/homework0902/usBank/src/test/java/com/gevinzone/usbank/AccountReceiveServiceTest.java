package com.gevinzone.usbank;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceToService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@SpringBootTest
public class AccountReceiveServiceTest {
    @Autowired
    private remittanceToService bankService;

    private AccountTransferDetail createDefaultAccountTransferDetail() {
        Date now = new Date();
        return AccountTransferDetail.builder()
                .id(2).fromId("cn-1").toId("us-1").amountUs(new BigDecimal(1)).amountCny(new BigDecimal(7))
                .status(0).createTime(now).updateTime(now).build();

    }

    @Test
    void testStartReceive() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.startReceiveMoney(detail);
    }

    @Test
    void testCompleteReceive() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.completeReceiveMoney(detail);
    }

    @Test
    void testCancelReceive() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.cancelReceiveMoney(detail);
    }
}
