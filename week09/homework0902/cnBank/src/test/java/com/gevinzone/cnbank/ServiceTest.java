package com.gevinzone.cnbank;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceFromService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@SpringBootTest
public class ServiceTest {
    @Autowired
    remittanceFromService bankService;

    private AccountTransferDetail createDefaultAccountTransferDetail() {
        Date now = new Date();
        return AccountTransferDetail.builder()
                .id(12).fromId("cn-1").toId("us-1").amountUs(new BigDecimal(1)).amountCny(new BigDecimal(7))
                .status(0).createTime(now).updateTime(now).build();

    }

    @Test
    void testStartPay() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.startPayMoney(detail);
    }

    @Test
    void testCompletePay() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.completePayMoney(detail);
    }

    @Test
    void testCancelPay() {
        AccountTransferDetail detail = createDefaultAccountTransferDetail();
        bankService.cancelPayMoney(detail);
    }
}
