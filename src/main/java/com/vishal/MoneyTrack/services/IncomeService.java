package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.IncomeRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IncomeService {
    IncomeResponse create(IncomeRequest request);
    IncomeResponse getById(UUID id);
    List<IncomeResponse> getAll();
    List<IncomeResponse> getByDateRange(LocalDate startDate, LocalDate endDate);
    List<IncomeResponse> getByAccountId(UUID accountId);
    IncomeResponse update(UUID id, IncomeRequest request);
    void delete(UUID id);
}

