package com.UniTech.UniTechTest.exception;

import java.math.BigDecimal;

public class NotEnoughAmountException extends RuntimeException{
    public NotEnoughAmountException(BigDecimal transferAmount) {
        super("Not enough balance for transfer amount: "+transferAmount);
    }
}
