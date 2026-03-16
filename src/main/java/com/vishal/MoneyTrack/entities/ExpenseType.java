package com.vishal.MoneyTrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_of_expense", uniqueConstraints = @UniqueConstraint(columnNames = {"type_name", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseType extends BaseEntity {

    @Column(name = "type_name", nullable = false, length = 100)
    private String typeName;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

