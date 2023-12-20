package com.UniTech.UniTechTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CacheObject {
    private String key;
    private BigDecimal value;
    private LocalDateTime endLive;

}
