package com.UniTech.UniTechTest.service;



import com.UniTech.UniTechTest.dto.AccountDTO;
import com.UniTech.UniTechTest.dto.CreateAccountRequest;
import com.UniTech.UniTechTest.dto.TopUpRequest;
import com.UniTech.UniTechTest.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account getAndValidateAccount(Long accountId);


    List<AccountDTO> getActiveAccountsByUserId(Long userId);

    void createAccount(CreateAccountRequest request, long userId);

    void topUp(TopUpRequest request);

    void updateAccountBalances(Account fromAccount, Account toAccount, BigDecimal transferAmount, BigDecimal convertedAmount);

    void addAccountBalance(Account account, BigDecimal transferAmount);
}
