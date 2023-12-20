package com.UniTech.UniTechTest.controller;

import com.UniTech.UniTechTest.dto.AuthenticationResponse;
import com.UniTech.UniTechTest.dto.LoginRequest;
import com.UniTech.UniTechTest.dto.RegisterRequest;
import com.UniTech.UniTechTest.exception.BadUserCredentialException;
import com.UniTech.UniTechTest.exception.UserIsExistWithThisPinException;
import com.UniTech.UniTechTest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws BadUserCredentialException {
        return authService.Login(loginRequest);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest registerRequest) throws UserIsExistWithThisPinException {
        return authService.Register(registerRequest);
    }

}


