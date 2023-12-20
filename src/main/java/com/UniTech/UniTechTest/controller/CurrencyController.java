package com.UniTech.UniTechTest.controller;

import com.UniTech.UniTechTest.enums.CurrencyType;
import com.UniTech.UniTechTest.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping("/rate/{from}/{to}")
    public BigDecimal getRate(@PathVariable CurrencyType from, @PathVariable CurrencyType to) {
        return currencyRateService.GetCurrentRate(from, to);
    }
}
