package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    Page<SubCategory> findByUserId(UUID userId, Pageable pageable);
    Page<SubCategory> findByCategoryIdAndUserId(UUID categoryId, UUID userId, Pageable pageable);
    Optional<SubCategory> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByNameAndCategoryIdAndUserId(String name, UUID categoryId, UUID userId);
}
