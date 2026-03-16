package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, UUID> {
    List<IncomeCategory> findByUserId(UUID userId);
    Optional<IncomeCategory> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByCategoryNameAndUserId(String categoryName, UUID userId);
}
