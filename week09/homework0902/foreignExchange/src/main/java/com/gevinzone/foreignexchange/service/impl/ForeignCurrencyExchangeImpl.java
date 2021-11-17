package com.gevinzone.foreignexchange.service.impl;

import com.gevinzone.foreignexchange.mapper.AccountTransferDetailMapper;
import com.gevinzone.foreignexchange.service.ForeignCurrencyExchange;
import com.gevinzone.remittancecontract.Const;
import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import com.gevinzone.remittancecontract.service.remittanceFromService;
import com.gevinzone.remittancecontract.service.remittanceToService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class ForeignCurrencyExchangeImpl implements ForeignCurrencyExchange {
    @Autowired
    AccountTransferDetailMapper accountTransferDetailMapper;
    @Autowired ForeignCurrencyExchange foreignCurrencyExchange;

    @DubboReference(version = "1.0.0", tag = "cnBank")
    private remittanceFromService fromService;

    @DubboReference(version = "1.0.0", tag = "usBank")
    private remittanceToService toService;

    @Override
    @Transactional
    public void transferFromCnToUs(long fromId, long toId, BigDecimal usAmount) {
        /*
            不了解转账汇款等相关业务，拟按以下业务逻辑开发相关功能：

            Try:
            1. 创建业务单，状态为0，记录汇款详情
            2. from 方操作：扣款开始，余额扣除款项，扣款日志增加操作记录和冻结金额（status=0）
            3. to 方操作：收款开始，将收到款项增之frozen字段

            Confirm:
            1. to 方操作：收款完成，将款项增之余额，并从frozen中扣除相应数据
            2. from 方操作：扣款完成，扣款日志标记为完成，冻结金额清零（status=1）
            3. 业务单状态改为1

            Cancel:
            1. from 方操作：若扣款日志中冻结金额不为零，回款，日志标记为回滚（status=2）
            2. to 方操作：冻结字段移除相关冻结款项
            3. 业务单状态标记为2
         */
        AccountTransferDetail detail = createAccountTransferDetail(Const.cnAccountPrefix+fromId, Const.usAccountPrefix+toId,
                usAmount.multiply(new BigDecimal(7)), usAmount);
//        detail.setId(1); // already created in db
        try {
            foreignCurrencyExchange.tryTransferFromCnToUs(detail);
        } catch (Exception exception) {
            log.info("exception occurs, roll back with tcc");
        }
    }


    public AccountTransferDetail createAccountTransferDetail(String fromId, String toId, BigDecimal cnyAmount, BigDecimal usAmount) {
        Date now = new Date();
        AccountTransferDetail detail = AccountTransferDetail.builder()
                .amountCny(cnyAmount).amountUs(usAmount).fromId(fromId).toId(toId)
                .createTime(now).updateTime(now).status(0).build();
        accountTransferDetailMapper.insert(detail);
        return detail;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmTransferFromCnToUs", cancelMethod = "cancelTransferFromCnToUs")
    public void tryTransferFromCnToUs(AccountTransferDetail detail) {
        log.info("start transfer...");
        fromService.startPayMoney(detail);
        toService.startReceiveMoney(detail);
//        throw new RuntimeException("test exception"); // Test cancel when throw exception
    }


    public void confirmTransferFromCnToUs(AccountTransferDetail detail) {
        log.info("complete transfer...");
        fromService.completePayMoney(detail);
        toService.completeReceiveMoney(detail);
        detail.setStatus(1);
        updateAccountTransferDetailStatus(detail);
    }

    public void cancelTransferFromCnToUs(AccountTransferDetail detail) {
        log.info("cancel transfer...");
        fromService.cancelPayMoney(detail);
        toService.cancelReceiveMoney(detail);
        detail.setStatus(2);
        updateAccountTransferDetailStatus(detail);
    }

    private void updateAccountTransferDetailStatus(AccountTransferDetail detail) {
        accountTransferDetailMapper.updateAccountTransferDetailStatus(detail);
    }
}
