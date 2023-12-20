package com.UniTech.UniTechTest.service;


import com.UniTech.UniTechTest.model.Account;

import java.math.BigDecimal;

public interface TransferHistoryService {
    void saveTransferHistory(Account fromAccount, Account toAccount, BigDecimal amount);
}
