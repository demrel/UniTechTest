package com.UniTech.UniTechTest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "AccountHistory")
public class AccountHistory {
    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime time;
}
