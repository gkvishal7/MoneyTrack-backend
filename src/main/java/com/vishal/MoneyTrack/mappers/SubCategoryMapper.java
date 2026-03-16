package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;
import com.vishal.MoneyTrack.entities.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryMapper {

    public SubCategory toEntity(SubCategoryRequest request) {
        SubCategory entity = new SubCategory();
        entity.setName(request.name());
        entity.setBudget(request.budget());
        entity.setDescription(request.description());
        return entity;
    }

    public SubCategoryResponse toResponse(SubCategory entity) {
        return new SubCategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getBudget(),
                entity.getCategory().getId(),
                entity.getCategory().getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(SubCategory entity, SubCategoryRequest request) {
        entity.setName(request.name());
        entity.setBudget(request.budget());
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
    }
}

