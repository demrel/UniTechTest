package com.UniTech.UniTechTest.exception;

public class TransferAmountException extends RuntimeException {
    public TransferAmountException() {
        super("minimal amount is less than 1");
    }
}
