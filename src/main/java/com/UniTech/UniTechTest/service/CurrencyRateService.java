package com.UniTech.UniTechTest.service;


import com.UniTech.UniTechTest.enums.CurrencyType;

import java.math.BigDecimal;

public interface CurrencyRateService {
    BigDecimal GetCurrentRate(CurrencyType from, CurrencyType to);
}
