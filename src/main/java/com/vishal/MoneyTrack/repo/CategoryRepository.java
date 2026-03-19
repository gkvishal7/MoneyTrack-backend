package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findByUserId(UUID userId, Pageable pageable);
    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByNameAndUserId(String name, UUID userId);
}
