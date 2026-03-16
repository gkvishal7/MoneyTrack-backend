package com.vishal.MoneyTrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "income_category", uniqueConstraints = @UniqueConstraint(columnNames = {"category_name", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeCategory extends BaseEntity {

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

