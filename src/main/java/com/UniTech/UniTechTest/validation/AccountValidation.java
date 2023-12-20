package com.UniTech.UniTechTest.validation;


import com.UniTech.UniTechTest.exception.InactiveAccountException;
import com.UniTech.UniTechTest.exception.NotEnoughAmountException;
import com.UniTech.UniTechTest.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountValidation {
    public void validateSufficientBalance(Account fromAccount, BigDecimal transferAmount) {
        if (fromAccount.getAmount().compareTo(transferAmount) < 0) {
            throw new NotEnoughAmountException(transferAmount);
        }
    }

    public void validateActiveAccount(Account account) {
        if (account.isNotActive()) {
            throw new InactiveAccountException(account.getId());
        }
    }
}
