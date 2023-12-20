package com.UniTech.UniTechTest.exception;

public class UserIsExistWithThisPinException extends RuntimeException{
    public UserIsExistWithThisPinException(String pin) {
        super("User is exist with this pin: "+pin);
    }
}
