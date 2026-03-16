package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, UUID> {
    List<ExpenseType> findByUserId(UUID userId);
    Optional<ExpenseType> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByTypeNameAndUserId(String typeName, UUID userId);
}
