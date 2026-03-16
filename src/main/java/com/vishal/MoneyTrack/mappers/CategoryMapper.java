package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.CategoryRequest;
import com.vishal.MoneyTrack.dto.responses.CategoryResponse;
import com.vishal.MoneyTrack.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        Category entity = new Category();
        entity.setName(request.name());
        entity.setBudget(request.budget());
        entity.setDescription(request.description());
        return entity;
    }

    public CategoryResponse toResponse(Category entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getBudget(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(Category entity, CategoryRequest request) {
        entity.setName(request.name());
        entity.setBudget(request.budget());
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
    }
}

