package com.gevinzone.usbank.service;

import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceToService;
import com.gevinzone.usbank.bo.ReceiveInfo;
import com.gevinzone.usbank.mapper.AccountReceiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@DubboService(version = "1.0.0", tag = "usBank", weight = 100)
public class BankService implements remittanceToService {
    @Autowired
    private AccountReceiveMapper mapper;

    @Transactional
    @Override
    public void startReceiveMoney(AccountTransferDetail detail) {
        /*
            to 方操作：收款开始，将收到款项增之frozen字段
         */
        log.info("Start receiving money, detail: {}", detail);
        ReceiveInfo info = ReceiveInfo.loadReceiveInfoFromAccountTransferDetail(detail);
        synchronized (this) {
            insertAccountReceiveLog(info);
            mapper.insertAccountFrozenAmount(info);
        }

    }

    private void insertAccountReceiveLog(ReceiveInfo info) {
        Date now = new Date();
        AccountChangeLog log = AccountChangeLog.builder().businessId(info.getBusinessId()).userId(info.getUserId())
                .amount(info.getAmount()).status(0).createTime(now).updateTime(now).build();
        mapper.insertAccountReceiveLog(log);
    }

    @Transactional
    @Override
    public void completeReceiveMoney(AccountTransferDetail detail) {
        /*
            to 方操作：收款完成，将款项增之余额，并从frozen中扣除相应数据
         */
        log.info("Complete receiving money, detail: {}", detail);
        ReceiveInfo info = ReceiveInfo.loadReceiveInfoFromAccountTransferDetail(detail);
        synchronized (this) {
            int count = mapper.updateAccountChangeLogForCompletion(info);
            if (count > 0) {
                mapper.updateAccountBalanceForCompletion(info);
            }
        }
    }

    @Transactional
    @Override
    public void cancelReceiveMoney(AccountTransferDetail detail) {
        /*
            to 方操作：冻结字段移除相关冻结款项
         */
        log.info("Cancel receiving money, detail: {}", detail);
        ReceiveInfo info = ReceiveInfo.loadReceiveInfoFromAccountTransferDetail(detail);
        synchronized (this) {
            int count = mapper.updateAccountChangeLogForCancel(info);
            if (count > 0) {
                mapper.releaseAccountFrozenForCancel(info);
            }
        }
    }
}
