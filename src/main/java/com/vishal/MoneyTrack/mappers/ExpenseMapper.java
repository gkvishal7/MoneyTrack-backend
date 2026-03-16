package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.ExpenseRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseResponse;
import com.vishal.MoneyTrack.entities.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public Expense toEntity(ExpenseRequest request) {
        Expense entity = new Expense();
        entity.setExpenseDate(request.expenseDate());
        entity.setAmount(request.amount());
        entity.setNotes(request.notes());
        return entity;
    }

    public ExpenseResponse toResponse(Expense entity) {
        return new ExpenseResponse(
                entity.getId(),
                entity.getExpenseDate(),
                entity.getExpenseType().getId(),
                entity.getExpenseType().getTypeName(),
                entity.getAmount(),
                entity.getCategory().getId(),
                entity.getCategory().getName(),
                entity.getSubCategory().getId(),
                entity.getSubCategory().getName(),
                entity.getAccount().getId(),
                entity.getAccount().getName(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(Expense entity, ExpenseRequest request) {
        entity.setExpenseDate(request.expenseDate());
        entity.setAmount(request.amount());
        if (request.notes() != null) {
            entity.setNotes(request.notes());
        }
    }
}

