package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseTypeService {
    ExpenseTypeResponse create(ExpenseTypeRequest request);
    ExpenseTypeResponse getById(UUID id);
    List<ExpenseTypeResponse> getAll();
    ExpenseTypeResponse update(UUID id, ExpenseTypeRequest request);
    void delete(UUID id);
}

