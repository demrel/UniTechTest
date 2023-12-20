package com.UniTech.UniTechTest.service;

import com.UniTech.UniTechTest.dto.TransferRequest;
import org.springframework.transaction.annotation.Transactional;

public interface TransferService {
    @Transactional
    void transfer(TransferRequest request);
}
