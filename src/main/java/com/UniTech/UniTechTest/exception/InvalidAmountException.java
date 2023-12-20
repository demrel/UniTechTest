package com.UniTech.UniTechTest.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException() {
        super("Amount can not be less than 1");
    }
}
