package com.UniTech.UniTechTest.validation;


import com.UniTech.UniTechTest.dto.TransferRequest;
import com.UniTech.UniTechTest.exception.SameAccountTransferException;
import com.UniTech.UniTechTest.exception.TransferAmountException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class TransferRequestValidation {

    public void validateTransferRequest(TransferRequest request) {
        if (Objects.equals(request.getFrom(), request.getTo())) {
            throw new SameAccountTransferException();
        }
        if(request.getAmount().compareTo(BigDecimal.valueOf(1))<0){
            throw new TransferAmountException();
        }
    }
}
