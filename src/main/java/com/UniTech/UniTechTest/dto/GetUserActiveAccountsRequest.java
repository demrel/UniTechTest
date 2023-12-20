package com.UniTech.UniTechTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class GetUserActiveAccountsRequest {
    private long id;
    private String name;
    private BigDecimal value;
    private String currency;
}
