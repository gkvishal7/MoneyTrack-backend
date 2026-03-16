package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponse create(AccountRequest request);
    AccountResponse getById(UUID id);
    List<AccountResponse> getAll();
    AccountResponse update(UUID id, AccountRequest request);
    void delete(UUID id);
    AccountResponse updateBalance(UUID id, java.math.BigDecimal newBalance);
}

