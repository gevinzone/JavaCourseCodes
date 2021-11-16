package com.gevinzone.foreignexchange;

import com.gevinzone.foreignexchange.service.ForeignCurrencyExchange;
import com.gevinzone.remittancecontract.IDemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class AppRunner implements ApplicationRunner {
    @DubboReference(version = "1.0.0", tag = "cnBank")
    private IDemo cnBankDemo;
    @DubboReference(version = "1.0.0", tag = "usBank")
    private IDemo usBankDemo;
    @Autowired
    ForeignCurrencyExchange currencyExchange;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(cnBankDemo.greeting());
        log.info(usBankDemo.greeting());
        log.info("*************test************");
        currencyExchange.transferFromCnToUs(1, 1, new BigDecimal(1));
    }
}
