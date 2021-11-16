package com.gevinzone.remittancecontract.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;

import java.math.BigDecimal;

public interface IForeignCurrencyService {
//    void transfer(AccountTransferDetail detail);
    void increaseAccount(Long userId, BigDecimal amount);
}
