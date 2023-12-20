package com.UniTech.UniTechTest.client;

import com.UniTech.UniTechTest.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class MockCurrencyRateServiceClient {
    @Autowired
    private  RestTemplate template;

    @Value("${server.baseUrl}")
    String basePath;
    public BigDecimal fetchMockCurrencyRate(CurrencyType from, CurrencyType to) {
        return template.getForObject( "http://localhost:8080/mock/currencyRate/" + from.toString() + "/" + to.toString(), BigDecimal.class);
    }
}
