package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponseMapper;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;
import com.vishal.MoneyTrack.entities.Category;
import com.vishal.MoneyTrack.entities.SubCategory;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.SubCategoryMapper;
import com.vishal.MoneyTrack.repo.CategoryRepository;
import com.vishal.MoneyTrack.repo.SubCategoryRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.SubCategoryService;
import com.vishal.MoneyTrack.utils.PageableUtils;
import com.vishal.MoneyTrack.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SubCategoryMapper mapper;

    @Override
    @Transactional
    public SubCategoryResponse create(SubCategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();

        Category category = categoryRepository.findByIdAndUserId(request.categoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        if (repository.existsByNameAndCategoryIdAndUserId(request.name(), request.categoryId(), userId)) {
            throw new DuplicateResourceException("Sub category with name '" + request.name() + "' already exists in this category");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SubCategory entity = mapper.toEntity(request);
        entity.setCategory(category);
        entity.setUser(user);
        SubCategory saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategoryResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        SubCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub category not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<SubCategoryResponse> getAll(int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<SubCategory> subCategoryPage = repository.findByUserId(userId, pageable);
        return PagedResponseMapper.toPagedResponse(subCategoryPage, mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<SubCategoryResponse> getByCategoryId(UUID categoryId, int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<SubCategory> subCategoryPage = repository.findByCategoryIdAndUserId(categoryId, userId, pageable);
        return PagedResponseMapper.toPagedResponse(subCategoryPage, mapper::toResponse);
    }

    @Override
    @Transactional
    public SubCategoryResponse update(UUID id, SubCategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        SubCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub category not found or access denied"));

        Category category = categoryRepository.findByIdAndUserId(request.categoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        boolean nameChanged = !entity.getName().equals(request.name());
        boolean categoryChanged = !entity.getCategory().getId().equals(request.categoryId());

        if ((nameChanged || categoryChanged) &&
                repository.existsByNameAndCategoryIdAndUserId(request.name(), request.categoryId(), userId)) {
            throw new DuplicateResourceException("Sub category with name '" + request.name() + "' already exists in this category");
        }

        mapper.updateEntity(entity, request);
        entity.setCategory(category);
        SubCategory updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        SubCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub category not found or access denied"));
        repository.delete(entity);
    }
}
