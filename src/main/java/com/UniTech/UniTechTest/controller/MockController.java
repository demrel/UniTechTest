package com.UniTech.UniTechTest.controller;

import com.UniTech.UniTechTest.dto.CheckPinResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockController {

    Map<String, BigDecimal> currencyRate = Map.of(
            "USD/AZN", BigDecimal.valueOf(1.7),
            "AZN/USD", BigDecimal.valueOf(0.7),
            "AZN/TL", BigDecimal.valueOf(8),
            "TL/AZN", BigDecimal.valueOf(0.8)
    );

    Map<String, CheckPinResponse> pinsResponse = Map.of(
            "1234567", new CheckPinResponse("Savion","Arias"),
            "AA34567", new CheckPinResponse("Kaden","Hanson"),
            "AZ34567", new CheckPinResponse("Hope","Kaiser"),
            "AB34567", new CheckPinResponse("Jacqueline","Lawson")
    );

    @GetMapping("/currencyRate/{from}/{to}")
    public BigDecimal currencyRate(@PathVariable String from, @PathVariable String to) {
       return currencyRate.get(from + "/" + to);
    }

    @GetMapping("/checkPin/{pin}")
    public CheckPinResponse checkPin(@PathVariable String pin) {
        return pinsResponse.get(pin);
    }
}
