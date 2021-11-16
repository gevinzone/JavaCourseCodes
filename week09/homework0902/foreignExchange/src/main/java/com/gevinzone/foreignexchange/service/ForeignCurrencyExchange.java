package com.gevinzone.foreignexchange.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;

import java.math.BigDecimal;

public interface ForeignCurrencyExchange {
    void transferFromCnToUs(long fromId, long toId, BigDecimal usAmount);
    void tryTransferFromCnToUs(AccountTransferDetail detail);
    AccountTransferDetail createAccountTransferDetail(String fromId, String toId, BigDecimal cnyAmount, BigDecimal usAmount);
}
