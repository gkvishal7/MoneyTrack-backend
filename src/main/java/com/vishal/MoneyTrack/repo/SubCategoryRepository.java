package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Category;
import com.vishal.MoneyTrack.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    List<SubCategory> findByUserId(UUID userId);
    List<SubCategory> findByCategoryIdAndUserId(UUID categoryId, UUID userId);
    Optional<SubCategory> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByNameAndCategoryIdAndUserId(String name, UUID categoryId, UUID userId);
}
