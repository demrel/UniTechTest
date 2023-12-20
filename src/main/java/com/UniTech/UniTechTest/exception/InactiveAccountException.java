package com.UniTech.UniTechTest.exception;

public class InactiveAccountException extends RuntimeException {
    public InactiveAccountException(long id) {
        super("This account is inactive " + id);
    }
}
