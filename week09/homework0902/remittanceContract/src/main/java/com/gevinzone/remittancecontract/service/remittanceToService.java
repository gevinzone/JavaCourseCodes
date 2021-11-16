package com.gevinzone.remittancecontract.service;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;

public interface remittanceToService {
    void startReceiveMoney(AccountTransferDetail detail);
    void completeReceiveMoney(AccountTransferDetail detail);
    void cancelReceiveMoney(AccountTransferDetail detail);
}
