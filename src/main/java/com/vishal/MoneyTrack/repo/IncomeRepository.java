package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    Page<Income> findByUserId(UUID userId, Pageable pageable);
    Optional<Income> findByIdAndUserId(UUID id, UUID userId);
    Page<Income> findByAccountIdAndUserId(UUID accountId, UUID userId, Pageable pageable);
    Page<Income> findByIncomeCategoryIdAndUserId(UUID incomeCategoryId, UUID userId, Pageable pageable);

    @Query("SELECT i FROM Income i WHERE i.incomeDate >= :startDate AND i.incomeDate <= :endDate AND i.user.id = :userId")
    Page<Income> findIncomesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") UUID userId, Pageable pageable);
}
