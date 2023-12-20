package com.UniTech.UniTechTest.service;


import com.UniTech.UniTechTest.dto.AuthenticationResponse;
import com.UniTech.UniTechTest.dto.LoginRequest;
import com.UniTech.UniTechTest.dto.RegisterRequest;
import com.UniTech.UniTechTest.exception.BadUserCredentialException;
import com.UniTech.UniTechTest.exception.UserIsExistWithThisPinException;

public interface AuthService {
    AuthenticationResponse Register(RegisterRequest request) throws UserIsExistWithThisPinException;
    AuthenticationResponse Login(LoginRequest request) throws BadUserCredentialException;
}
