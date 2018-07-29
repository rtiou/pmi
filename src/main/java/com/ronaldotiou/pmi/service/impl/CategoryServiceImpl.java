package com.ronaldotiou.pmi.service.impl;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.exception.ResourceNotFoundException;
import com.ronaldotiou.pmi.jpa.repository.CategoryRepository;
import com.ronaldotiou.pmi.mapper.CategoryMapper;
import com.ronaldotiou.pmi.service.CategoryService;
import com.ronaldotiou.pmi.service.ParseFileCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ParseFileCategory parse;

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Override
    public List<Category> getCategories() {
        return mapper.jpaToDomain(repository.findAll());
    }

    @Override
    public Category getCategory(String categoryId) {
        if (repository.existsById(categoryId)) {
            return mapper.jpaToDomain(repository.getOne(categoryId));
        }
        throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
    }

    @Override
    public Category saveCategory(Category category) {
        com.ronaldotiou.pmi.jpa.model.Category jpaCategory = mapper.domainToJpa(category);
        jpaCategory = repository.save(jpaCategory);
        return mapper.jpaToDomain(jpaCategory);
    }

    @Override
    public void saveUpload(MultipartFile file) {
        List<Category> categories = parse.streamToBean(file);
        repository.saveAll(mapper.domainToJpa(categories));
    }

    @Override
    public void delete(String categoryId) {
        if (!repository.existsById(categoryId)) {
            throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
        }
        repository.deleteById(categoryId);
    }

    @Override
    public Category update(String categoryId, Category category) {
        if (!repository.existsById(categoryId)) {
            throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
        }

        com.ronaldotiou.pmi.jpa.model.Category categoryJpa = repository.findById(categoryId).get();

        categoryJpa.setName(category.getName());

        categoryJpa = repository.save(categoryJpa);
        return mapper.jpaToDomain(categoryJpa);
    }
}
