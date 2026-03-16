package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.IncomeRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeResponse;
import com.vishal.MoneyTrack.entities.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public Income toEntity(IncomeRequest request) {
        Income entity = new Income();
        entity.setIncomeDate(request.incomeDate());
        entity.setSourceOfIncome(request.sourceOfIncome());
        entity.setAmount(request.amount());
        entity.setNotes(request.notes());
        return entity;
    }

    public IncomeResponse toResponse(Income entity) {
        return new IncomeResponse(
                entity.getId(),
                entity.getIncomeDate(),
                entity.getSourceOfIncome(),
                entity.getAmount(),
                entity.getAccount().getId(),
                entity.getAccount().getName(),
                entity.getIncomeCategory().getId(),
                entity.getIncomeCategory().getCategoryName(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(Income entity, IncomeRequest request) {
        entity.setIncomeDate(request.incomeDate());
        entity.setSourceOfIncome(request.sourceOfIncome());
        entity.setAmount(request.amount());
        if (request.notes() != null) {
            entity.setNotes(request.notes());
        }
    }
}

