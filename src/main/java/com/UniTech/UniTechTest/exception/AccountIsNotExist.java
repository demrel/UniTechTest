package com.UniTech.UniTechTest.exception;

public class AccountIsNotExist extends RuntimeException {
    public AccountIsNotExist() {
        super("Account is not exist");
    }
}
