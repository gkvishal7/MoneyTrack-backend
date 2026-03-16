package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.config.SecurityUtils;
import com.vishal.MoneyTrack.dto.requests.ExpenseRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseResponse;
import com.vishal.MoneyTrack.entities.*;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.ExpenseMapper;
import com.vishal.MoneyTrack.repo.*;
import com.vishal.MoneyTrack.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper mapper;

    @Override
    @Transactional
    public ExpenseResponse create(ExpenseRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        
        ExpenseType expenseType = expenseTypeRepository.findByIdAndUserId(request.expenseTypeId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found or access denied"));
        
        Category category = categoryRepository.findByIdAndUserId(request.categoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));
        
        SubCategory subCategory = subCategoryRepository.findByIdAndUserId(request.subCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub category not found or access denied"));
        
        Account account = accountRepository.findByIdAndUserId(request.accountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Expense entity = mapper.toEntity(request);
        entity.setExpenseType(expenseType);
        entity.setCategory(category);
        entity.setSubCategory(subCategory);
        entity.setAccount(account);
        entity.setUser(user);
        
        Expense saved = expenseRepository.save(entity);
        
        // Update account balance
        BigDecimal newBalance = account.getBalance().subtract(request.amount());
        account.setBalance(newBalance);
        accountRepository.save(account);
        
        return mapper.toResponse(saved);
    }

    @Override
    public ExpenseResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Expense entity = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    public List<ExpenseResponse> getAll() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return expenseRepository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ExpenseResponse> getByDateRange(LocalDate startDate, LocalDate endDate) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return expenseRepository.findExpensesByDateRange(startDate, endDate, userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ExpenseResponse> getByAccountId(UUID accountId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return expenseRepository.findByAccountIdAndUserId(accountId, userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ExpenseResponse> getByCategoryId(UUID categoryId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return expenseRepository.findByCategoryIdAndUserId(categoryId, userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ExpenseResponse> getBySubCategoryId(UUID subCategoryId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return expenseRepository.findBySubCategoryIdAndUserId(subCategoryId, userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ExpenseResponse update(UUID id, ExpenseRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Expense entity = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found or access denied"));
        
        Account oldAccount = entity.getAccount();
        BigDecimal oldAmount = entity.getAmount();
        
        ExpenseType expenseType = expenseTypeRepository.findByIdAndUserId(request.expenseTypeId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found or access denied"));
        
        Category category = categoryRepository.findByIdAndUserId(request.categoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));
        
        SubCategory subCategory = subCategoryRepository.findByIdAndUserId(request.subCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub category not found or access denied"));
        
        Account newAccount = accountRepository.findByIdAndUserId(request.accountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));
        
        mapper.updateEntity(entity, request);
        entity.setExpenseType(expenseType);
        entity.setCategory(category);
        entity.setSubCategory(subCategory);
        entity.setAccount(newAccount);
        
        Expense updated = expenseRepository.save(entity);
        
        // Update account balances
        if (!oldAccount.getId().equals(newAccount.getId())) {
            // Different accounts - revert old, apply new
            oldAccount.setBalance(oldAccount.getBalance().add(oldAmount));
            accountRepository.save(oldAccount);
            newAccount.setBalance(newAccount.getBalance().subtract(request.amount()));
            accountRepository.save(newAccount);
        } else {
            // Same account - adjust difference
            BigDecimal difference = oldAmount.subtract(request.amount());
            newAccount.setBalance(newAccount.getBalance().add(difference));
            accountRepository.save(newAccount);
        }
        
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Expense entity = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found or access denied"));
        
        Account account = entity.getAccount();
        account.setBalance(account.getBalance().add(entity.getAmount()));
        accountRepository.save(account);
        
        expenseRepository.delete(entity);
    }
}
