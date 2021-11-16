package com.gevinzone.cnbank.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceFromService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService(version = "1.0.0", tag = "cnBank", weight = 100)
public class BankService implements remittanceFromService {
    @Override
    public void startPayMoney(AccountTransferDetail detail) {
        /*
         from 方操作：扣款开始，余额扣除款项，扣款日志增加操作记录和冻结金额（status=0）
         */
        log.info("Start paying money, detail: {}", detail);
    }

    @Override
    public void completePayMoney(AccountTransferDetail detail) {
        log.info("Complete paying money, detail: {}", detail);
    }

    @Override
    public void cancelPayMoney(AccountTransferDetail detail) {
        log.info("cancel paying money, detail: {}", detail);
    }

//    @Override
//    public String testMethod(String data) {
//        return data;
//    }
}
