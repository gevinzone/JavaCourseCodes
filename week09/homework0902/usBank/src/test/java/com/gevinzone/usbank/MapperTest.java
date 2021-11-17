package com.gevinzone.usbank;

import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import com.gevinzone.usbank.bo.ReceiveInfo;
import com.gevinzone.usbank.mapper.AccountReceiveMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class MapperTest {
    @Autowired
    private AccountReceiveMapper mapper;

    @Test
    void testInsertAccountReceiveLog() {
        Date now = new Date();
        AccountChangeLog log = AccountChangeLog.builder().userId(1).businessId(3).amount(new BigDecimal(1))
                .createTime(now).updateTime(now).status(0).build();
        Assertions.assertEquals(1, mapper.insertAccountReceiveLog(log));
    }

    @Test
    void testInsertAccountFrozenAmount() {
        ReceiveInfo info = ReceiveInfo.builder().businessId(1).userId(1).amount(new BigDecimal(1))
                .updateTime(new Date()).build();
        Assertions.assertEquals(1, mapper.insertAccountFrozenAmount(info));
    }
}
