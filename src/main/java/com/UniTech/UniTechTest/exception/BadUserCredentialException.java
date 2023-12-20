package com.UniTech.UniTechTest.exception;

public class BadUserCredentialException extends RuntimeException{
    public BadUserCredentialException() {
        super("Bad user credentials");
    }
}
