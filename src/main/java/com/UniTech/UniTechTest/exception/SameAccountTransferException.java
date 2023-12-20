package com.UniTech.UniTechTest.exception;

public class SameAccountTransferException extends RuntimeException{
    public SameAccountTransferException() {
        super("You can not transfer same account");
    }
}
