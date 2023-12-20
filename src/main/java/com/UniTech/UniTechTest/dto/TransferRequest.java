package com.UniTech.UniTechTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
public class TransferRequest {
    private BigDecimal amount;
    private long from;
    private long to;
}
