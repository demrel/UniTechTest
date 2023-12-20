package com.UniTech.UniTechTest.dto;

import com.UniTech.UniTechTest.enums.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    private CurrencyType currencyType;
    private BigDecimal amount;
}
