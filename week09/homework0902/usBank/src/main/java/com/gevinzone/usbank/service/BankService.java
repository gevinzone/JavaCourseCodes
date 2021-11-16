package com.gevinzone.usbank.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceToService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService(version = "1.0.0", tag = "usBank", weight = 100)
public class BankService implements remittanceToService {
    @Override
    public void startReceiveMoney(AccountTransferDetail detail) {
        log.info("Start receiving money, detail: {}", detail);
    }

    @Override
    public void completeReceiveMoney(AccountTransferDetail detail) {
        log.info("Complete receiving money, detail: {}", detail);
    }

    @Override
    public void cancelReceiveMoney(AccountTransferDetail detail) {
        log.info("Cancel receiving money, detail: {}", detail);
    }
}
