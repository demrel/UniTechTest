package com.UniTech.UniTechTest.serviceimpl;

import com.UniTech.UniTechTest.client.MockCurrencyRateServiceClient;
import com.UniTech.UniTechTest.enums.CurrencyType;
import com.UniTech.UniTechTest.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class CurrencyRateServiceImp implements CurrencyRateService {
    @Autowired
    private  RedisTemplate<String, BigDecimal> redisTemplate;

    private final MockCurrencyRateServiceClient mockCurrencyRateServiceClient;
    @Autowired
    public CurrencyRateServiceImp(MockCurrencyRateServiceClient mockCurrencyRateServiceClient) {
        this.mockCurrencyRateServiceClient = mockCurrencyRateServiceClient;
    }

    @Override
    public BigDecimal GetCurrentRate(CurrencyType from, CurrencyType to) {
        var key = GenerateKey(from, to);
        var rate = redisTemplate.opsForValue().get(key);
        if (rate == null) {
            rate = mockCurrencyRateServiceClient.fetchMockCurrencyRate(from, to);
            redisTemplate.opsForValue().set(key, rate, CalculateDuration(), TimeUnit.SECONDS);
        }
        return rate;
    }

    private long CalculateDuration() {
        LocalTime time = LocalTime.now();
        return 60 - time.getSecond();
    }

    private String GenerateKey(CurrencyType from, CurrencyType to) {
        return from + "/" + to;
    }
}
