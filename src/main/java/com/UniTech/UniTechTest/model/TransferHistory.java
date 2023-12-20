package com.UniTech.UniTechTest.model;

import jakarta.persistence.*;
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
@Table(name = "TransferHistory")
public class TransferHistory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fromAccountId")
    private Account fromAccount;
    @ManyToOne
    @JoinColumn(name = "toAccountId")
    private Account toAccount;
    private BigDecimal amount;
    private LocalDateTime transferTime;
}
