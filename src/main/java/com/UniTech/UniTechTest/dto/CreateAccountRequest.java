package com.UniTech.UniTechTest.dto;

import com.UniTech.UniTechTest.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateAccountRequest {
    private CurrencyType currency;
    private BigDecimal initialAmount;
}
