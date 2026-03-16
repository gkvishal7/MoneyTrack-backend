package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.config.SecurityUtils;
import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;
import com.vishal.MoneyTrack.entities.ExpenseType;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.ExpenseTypeMapper;
import com.vishal.MoneyTrack.repo.ExpenseTypeRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepository repository;
    private final UserRepository userRepository;
    private final ExpenseTypeMapper mapper;

    @Override
    @Transactional
    public ExpenseTypeResponse create(ExpenseTypeRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        if (repository.existsByTypeNameAndUserId(request.typeName(), userId)) {
            throw new DuplicateResourceException("Expense type with name '" + request.typeName() + "' already exists for this user");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        ExpenseType entity = mapper.toEntity(request);
        entity.setUser(user);
        ExpenseType saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    public ExpenseTypeResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        ExpenseType entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    public List<ExpenseTypeResponse> getAll() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ExpenseTypeResponse update(UUID id, ExpenseTypeRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        ExpenseType entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found or access denied"));
        
        if (!entity.getTypeName().equals(request.typeName()) && repository.existsByTypeNameAndUserId(request.typeName(), userId)) {
            throw new DuplicateResourceException("Expense type with name '" + request.typeName() + "' already exists for this user");
        }
        
        mapper.updateEntity(entity, request);
        ExpenseType updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        ExpenseType entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found or access denied"));
        repository.delete(entity);
    }
}
