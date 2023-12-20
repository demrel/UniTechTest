package com.UniTech.UniTechTest.model;

import com.UniTech.UniTechTest.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private CurrencyType currencyType;
    private BigDecimal amount;
    private boolean isActive;

    public boolean isNotActive() {
        return !isActive;
    }
}
