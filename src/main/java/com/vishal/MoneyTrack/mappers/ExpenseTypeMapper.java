package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;
import com.vishal.MoneyTrack.entities.ExpenseType;
import org.springframework.stereotype.Component;

@Component
public class ExpenseTypeMapper {

    public ExpenseType toEntity(ExpenseTypeRequest request) {
        ExpenseType entity = new ExpenseType();
        entity.setTypeName(request.typeName());
        entity.setDescription(request.description());
        return entity;
    }

    public ExpenseTypeResponse toResponse(ExpenseType entity) {
        return new ExpenseTypeResponse(
                entity.getId(),
                entity.getTypeName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(ExpenseType entity, ExpenseTypeRequest request) {
        entity.setTypeName(request.typeName());
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
    }
}

