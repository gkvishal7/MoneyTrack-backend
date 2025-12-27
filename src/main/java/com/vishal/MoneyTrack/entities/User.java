package com.vishal.MoneyTrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", indexes = @Index(name = "idx_emailId", columnList = "email_id", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "email_id", nullable = false, unique = true, length = 255)
    private String emailId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}

