package com.UniTech.UniTechTest.controller;

import com.UniTech.UniTechTest.dto.AccountDTO;
import com.UniTech.UniTechTest.dto.CreateAccountRequest;
import com.UniTech.UniTechTest.dto.TopUpRequest;
import com.UniTech.UniTechTest.service.AccountService;
import com.UniTech.UniTechTest.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountsController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtUtil jwtUtil;

    @PutMapping("/topup")
    public ResponseEntity<?> topUp(@RequestBody TopUpRequest request) {
        accountService.topUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody CreateAccountRequest request) {
        var longId = Long.getLong(jwtUtil.extractUserId(auth));
        accountService.createAccount(request, longId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<AccountDTO> getActiveAccountsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        var longId = Long.getLong(jwtUtil.extractUserId(auth));
        return accountService.getActiveAccountsByUserId(longId);
    }


}
