package com.gevinzone.cnbank.service;

import com.gevinzone.cnbank.bo.PayInfo;
import com.gevinzone.cnbank.mapper.AccountPayMapper;
import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceFromService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@DubboService(version = "1.0.0", tag = "cnBank", weight = 100)
public class BankService implements remittanceFromService {
    @Autowired
    private AccountPayMapper mapper;

    @Override
    @Transactional
    public void startPayMoney(AccountTransferDetail detail) {
        /*
         from 方操作：扣款开始，余额扣除款项，扣款日志增加操作记录和冻结金额（status=0）
         */
        log.info("Start paying money, detail: {}", detail);
        PayInfo info = PayInfo.loadPayInfoFromAccountTransferDetail(detail);
        decreaseMoney(info);
    }

    @Transactional(rollbackFor = Exception.class)
    public void decreaseMoney(PayInfo info) {
        synchronized (this) {
            addPayLog(info);
            mapper.decreaseAccount(info);
        }
    }

    private void addPayLog(PayInfo info) {
        Date now = new Date();
//        info.setBusinessId(1); // simulate throw exception
        AccountChangeLog log = AccountChangeLog.builder().businessId(info.getBusinessId()).userId(info.getUserId())
                .amount(info.getAmount()).status(0).createTime(now).updateTime(now).build();
        mapper.insertAccountChangeLog(log);
    }

    @Transactional
    @Override
    public void completePayMoney(AccountTransferDetail detail) {
        /*
            from 方操作：扣款完成，扣款日志标记为完成，冻结金额清零（status=1）
         */
        log.info("Complete paying money, detail: {}", detail);

        PayInfo info = PayInfo.loadPayInfoFromAccountTransferDetail(detail);

        updatePayLogForComplete(info);
    }

    private void updatePayLogForComplete(PayInfo info) {
        synchronized (this) {
            int count = mapper.updateAccountChangeLogForCompletion(info);
            if (count > 0) {
                mapper.updateAccountFrozen(info);
            }
        }
    }

    @Transactional
    @Override
    public void cancelPayMoney(AccountTransferDetail detail) {
        /*
            from 方操作：若扣款日志中冻结金额不为零，回款，日志标记为回滚（status=2）
         */
        log.info("cancel paying money, detail: {}", detail);
        PayInfo info = PayInfo.loadPayInfoFromAccountTransferDetail(detail);
        updatePayLogForCancel(info);
    }


    private void updatePayLogForCancel(PayInfo info) {
        synchronized (this) {
            int count = mapper.updateAccountChangeLogForCancel(info);
            if (count > 0) {
                mapper.rollbackAccountBalance(info);
            }
        }
    }

}
