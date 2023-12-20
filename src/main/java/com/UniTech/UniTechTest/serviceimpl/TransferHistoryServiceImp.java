package com.UniTech.UniTechTest.serviceimpl;

import com.UniTech.UniTechTest.model.Account;
import com.UniTech.UniTechTest.model.TransferHistory;
import com.UniTech.UniTechTest.repository.TransferRepository;
import com.UniTech.UniTechTest.service.TransferHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Service
public class TransferHistoryServiceImp implements TransferHistoryService {
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public void saveTransferHistory(Account fromAccount, Account toAccount, BigDecimal amount) {
        transferRepository.save(
                TransferHistory.builder()
                        .fromAccount(fromAccount)
                        .toAccount(toAccount)
                        .amount(amount)
                        .transferTime(LocalDateTime.now())
                        .build()
        );
    }
}
