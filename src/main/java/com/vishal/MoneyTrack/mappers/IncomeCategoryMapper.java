package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;
import com.vishal.MoneyTrack.entities.IncomeCategory;
import org.springframework.stereotype.Component;

@Component
public class IncomeCategoryMapper {

    public IncomeCategory toEntity(IncomeCategoryRequest request) {
        IncomeCategory entity = new IncomeCategory();
        entity.setCategoryName(request.categoryName());
        entity.setDescription(request.description());
        return entity;
    }

    public IncomeCategoryResponse toResponse(IncomeCategory entity) {
        return new IncomeCategoryResponse(
                entity.getId(),
                entity.getCategoryName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(IncomeCategory entity, IncomeCategoryRequest request) {
        entity.setCategoryName(request.categoryName());
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
    }
}

