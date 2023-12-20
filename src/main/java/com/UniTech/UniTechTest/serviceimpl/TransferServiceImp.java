package com.UniTech.UniTechTest.serviceimpl;

import com.UniTech.UniTechTest.dto.TransferRequest;
import com.UniTech.UniTechTest.model.Account;
import com.UniTech.UniTechTest.service.AccountService;
import com.UniTech.UniTechTest.service.CurrencyRateService;
import com.UniTech.UniTechTest.service.TransferHistoryService;
import com.UniTech.UniTechTest.service.TransferService;
import com.UniTech.UniTechTest.validation.AccountValidation;
import com.UniTech.UniTechTest.validation.TransferRequestValidation;
import lombok.AllArgsConstructor;
import com.UniTech.UniTechTest.enums.CurrencyType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class TransferServiceImp implements TransferService {

    private final AccountValidation accountValidation;
    private final TransferRequestValidation transferRequestValidation;
    private final CurrencyRateService currencyRateService;
    private final AccountService accountService;
    private final TransferHistoryService transferHistoryService;


    @Override
    @Transactional
    public void transfer(TransferRequest request) {
        transferRequestValidation.validateTransferRequest(request);

        Account fromAccount = accountService.getAndValidateAccount(request.getFrom());
        accountValidation.validateActiveAccount(fromAccount);
        accountValidation.validateSufficientBalance(fromAccount, request.getAmount());

        Account toAccount = accountService.getAndValidateAccount(request.getTo());
        accountValidation.validateActiveAccount(toAccount);

        BigDecimal convertedAmount = convertTransferAmount(fromAccount.getCurrencyType(), toAccount.getCurrencyType(), request.getAmount());

        accountService.updateAccountBalances(fromAccount, toAccount, request.getAmount(), convertedAmount);
        transferHistoryService.saveTransferHistory(fromAccount, toAccount, convertedAmount);
    }

    private BigDecimal convertTransferAmount(CurrencyType from, CurrencyType to, BigDecimal amount) {
        if (from == to)
            return amount;
        var rate = currencyRateService.GetCurrentRate(from, to);
        return amount.multiply(rate);
    }
}
