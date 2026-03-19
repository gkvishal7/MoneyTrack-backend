package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, UUID> {
    Page<ExpenseType> findByUserId(UUID userId, Pageable pageable);
    Optional<ExpenseType> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByTypeNameAndUserId(String typeName, UUID userId);
}
