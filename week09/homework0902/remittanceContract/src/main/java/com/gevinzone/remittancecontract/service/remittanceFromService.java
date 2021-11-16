package com.gevinzone.remittancecontract.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;

public interface remittanceFromService {
    void startPayMoney(AccountTransferDetail detail);
    void completePayMoney(AccountTransferDetail detail);
    void cancelPayMoney(AccountTransferDetail detail);
//    String testMethod(String data);
}
