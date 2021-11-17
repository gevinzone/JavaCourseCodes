package com.gevinzone.cnbank;

import com.gevinzone.cnbank.bo.PayInfo;
import com.gevinzone.cnbank.mapper.AccountPayMapper;
import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@SpringBootTest
public class MapperTest {
    @Autowired
    private AccountPayMapper mapper;

//    @Test
    void insertAccountChangeLog() {
        Date now = new Date();
        AccountChangeLog log = AccountChangeLog.builder().userId(1).businessId(3).amount(new BigDecimal(7))
                .createTime(now).updateTime(now).status(0).build();
        int count = mapper.insertAccountChangeLog(log);
        Assertions.assertEquals(1, count);
    }

    @Test
    void decreaseAccount() {
        PayInfo info = PayInfo.builder().amount(new BigDecimal(10)).userId(1).build();
        int count = mapper.decreaseAccount(info);
        Assertions.assertEquals(1, count);
    }
}
