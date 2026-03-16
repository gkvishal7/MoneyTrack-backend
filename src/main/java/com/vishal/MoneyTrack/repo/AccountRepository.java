package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);
    Optional<Account> findByIdAndUserId(UUID id, UUID userId);
    Optional<Account> findByNameAndUserId(String name, UUID userId);
    boolean existsByNameAndUserId(String name, UUID userId);
}

