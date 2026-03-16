package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findByUserId(UUID userId);
    List<Income> findByIncomeDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, UUID userId);
    List<Income> findByAccountIdAndUserId(UUID accountId, UUID userId);
    List<Income> findByIncomeCategoryIdAndUserId(UUID incomeCategoryId, UUID userId);
    Optional<Income> findByIdAndUserId(UUID id, UUID userId);
    
    @Query("SELECT i FROM Income i WHERE i.incomeDate >= :startDate AND i.incomeDate <= :endDate AND i.user.id = :userId")
    List<Income> findIncomesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") UUID userId);
}

