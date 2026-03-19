package com.vishal.MoneyTrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "income", indexes = {
        @Index(name = "idx_income_date", columnList = "income_date"),
        @Index(name = "idx_income_category_id", columnList = "income_category_id"),
        @Index(name = "idx_income_account_id", columnList = "account_id"),
        @Index(name = "idx_income_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Income extends BaseEntity {

    @Column(name = "income_date", nullable = false)
    private LocalDate incomeDate;

    @Column(name = "source_of_income", nullable = false, length = 200)
    private String sourceOfIncome;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;

    @Column(name = "notes", length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

