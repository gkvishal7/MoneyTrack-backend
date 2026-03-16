package com.vishal.MoneyTrack.mappers;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;
import com.vishal.MoneyTrack.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(AccountRequest request) {
        Account entity = new Account();
        entity.setName(request.name());
        entity.setBalance(request.balance());
        entity.setDescription(request.description());
        return entity;
    }

    public AccountResponse toResponse(Account entity) {
        return new AccountResponse(
                entity.getId(),
                entity.getName(),
                entity.getBalance(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(Account entity, AccountRequest request) {
        entity.setName(request.name());
        entity.setBalance(request.balance());
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
    }
}

