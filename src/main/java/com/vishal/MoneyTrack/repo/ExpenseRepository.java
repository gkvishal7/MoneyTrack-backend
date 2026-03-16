package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUserId(UUID userId);
    List<Expense> findByExpenseDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, UUID userId);
    List<Expense> findByAccountIdAndUserId(UUID accountId, UUID userId);
    List<Expense> findByCategoryIdAndUserId(UUID categoryId, UUID userId);
    List<Expense> findBySubCategoryIdAndUserId(UUID subCategoryId, UUID userId);
    List<Expense> findByExpenseTypeIdAndUserId(UUID expenseTypeId, UUID userId);
    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);
    
    @Query("SELECT e FROM Expense e WHERE e.expenseDate >= :startDate AND e.expenseDate <= :endDate AND e.user.id = :userId")
    List<Expense> findExpensesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") UUID userId);
}

