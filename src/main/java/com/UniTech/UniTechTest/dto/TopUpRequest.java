package com.UniTech.UniTechTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TopUpRequest {
    private BigDecimal amount;
    private long accountId;
}
