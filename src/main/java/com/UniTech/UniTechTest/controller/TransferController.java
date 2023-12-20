package com.UniTech.UniTechTest.controller;

import com.UniTech.UniTechTest.dto.TransferRequest;
import com.UniTech.UniTechTest.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    @Autowired
    private TransferService transferService;
    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        transferService.transfer(request);
        return ResponseEntity.ok().build();
    }
}
