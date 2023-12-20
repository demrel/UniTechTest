package com.UniTech.UniTechTest.serviceimpl;


import com.UniTech.UniTechTest.dto.AccountDTO;
import com.UniTech.UniTechTest.dto.CreateAccountRequest;
import com.UniTech.UniTechTest.dto.TopUpRequest;
import com.UniTech.UniTechTest.exception.AccountNotFoundException;
import com.UniTech.UniTechTest.exception.TransferAmountException;
import com.UniTech.UniTechTest.model.Account;
import com.UniTech.UniTechTest.model.AccountHistory;
import com.UniTech.UniTechTest.repository.AccountHistoryRepository;
import com.UniTech.UniTechTest.repository.AccountRepository;
import com.UniTech.UniTechTest.repository.UserRepository;
import com.UniTech.UniTechTest.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    public AccountServiceImp(AccountRepository accountRepository, UserRepository userRepository, AccountHistoryRepository accountHistoryRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountHistoryRepository = accountHistoryRepository;
    }

    @Override
    public Account getAndValidateAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Override
    public List<AccountDTO> getActiveAccountsByUserId(Long userId) {
        List<Account> activeAccounts = accountRepository.findAllByIsActiveTrueAndUserId(userId);
        return activeAccounts.stream()
                .map(this::mapToAccountDTO)
                .collect(Collectors.toList());
    }

    private AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setCurrencyType(account.getCurrencyType());
        accountDTO.setAmount(account.getAmount());
        return accountDTO;
    }

    @Override
    public void createAccount(CreateAccountRequest request, long userId) {
        if (request.getInitialAmount().compareTo(BigDecimal.valueOf(1)) < 0) {
            throw new TransferAmountException();
        }

        var user = userRepository.getReferenceById(userId);

        accountRepository.save(Account
                .builder()
                .amount(request.getInitialAmount())
                .currencyType(request.getCurrency())
                .user(user)
                .isActive(true)
                .build());
    }

    @Override
    public void topUp(TopUpRequest request) {
        if (request.getAmount().compareTo(BigDecimal.valueOf(1)) < 0) {
            throw new TransferAmountException();
        }
        Account account = getAndValidateAccount(request.getAccountId());
        addAccountBalance(account, request.getAmount());
    }


    @Override
    public void updateAccountBalances(Account fromAccount, Account toAccount, BigDecimal transferAmount, BigDecimal convertedAmount) {
        addAccountBalance(fromAccount, transferAmount.negate());
        addAccountBalance(toAccount, convertedAmount);
    }
    @Override
    public void addAccountBalance(Account account, BigDecimal transferAmount) {
        account.setAmount(account.getAmount().add(transferAmount));
        accountHistoryRepository.save(AccountHistory.builder()
                .accountId(account.getId())
                .time(LocalDateTime.now())
                .amount(transferAmount)
                .build());
        accountRepository.save(account);
    }




}
