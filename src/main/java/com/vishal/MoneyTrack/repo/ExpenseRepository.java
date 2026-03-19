package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Expense;
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
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Page<Expense> findByUserId(UUID userId, Pageable pageable);
    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);
    Page<Expense> findByAccountIdAndUserId(UUID accountId, UUID userId, Pageable pageable);
    Page<Expense> findByCategoryIdAndUserId(UUID categoryId, UUID userId, Pageable pageable);
    Page<Expense> findBySubCategoryIdAndUserId(UUID subCategoryId, UUID userId, Pageable pageable);
    Page<Expense> findByExpenseTypeIdAndUserId(UUID expenseTypeId, UUID userId, Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.expenseDate >= :startDate AND e.expenseDate <= :endDate AND e.user.id = :userId")
    Page<Expense> findExpensesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") UUID userId, Pageable pageable);
}
