package com.gevinzone.foreignexchange;

import com.gevinzone.foreignexchange.service.ForeignCurrencyExchange;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Slf4j
@SpringBootTest
public class ForeignExchangeApplicationTest {
    @Autowired
    ForeignCurrencyExchange currencyExchange;
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
    }

//    @Test
//    void createAccountTransferDetail() {
//        AccountTransferDetail detail = currencyExchange.createAccountTransferDetail("1", "1", new BigDecimal(7), new BigDecimal(1));
//        log.info(detail.toString());
//    }
}
