package com.ronaldotiou.pmi.service.impl;

import static com.ronaldotiou.pmi.service.helper.TestHelper.createCategoryDomain;
import static com.ronaldotiou.pmi.service.helper.TestHelper.createCategoryJpaModel;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.jpa.repository.CategoryRepository;
import com.ronaldotiou.pmi.mapper.CategoryMapper;
import com.ronaldotiou.pmi.service.ParseFileCategory;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceImplTest {

    @Mock
    ParseFileCategory parse;

    @Mock
    CategoryRepository repository;

    @Mock
    CategoryMapper mapper;

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCategories() throws Exception {
        when(mapper.jpaToDomain(any(List.class))).thenReturn(Arrays.<Category>asList(createCategoryDomain()));
        when(repository.findAll()).thenReturn(Arrays.asList(createCategoryJpaModel()));

        List<Category> result = categoryServiceImpl.getCategories();

        assertEquals("categoryID", result.get(0).getCategoryID());
        assertEquals("name", result.get(0).getName());

    }

    @Test
    public void testGetCategory() throws Exception {
        when(mapper.jpaToDomain(any(com.ronaldotiou.pmi.jpa.model.Category.class))).thenReturn(createCategoryDomain());
        when(repository.existsById(any())).thenReturn(true);
        when(repository.getOne(any())).thenReturn(createCategoryJpaModel());

        Category result = categoryServiceImpl.getCategory("categoryId");
        assertEquals("categoryID", result.getCategoryID());
        assertEquals("name", result.getName());
    }

    @Test
    public void testSaveCategory() throws Exception {
        when(mapper.jpaToDomain(any(com.ronaldotiou.pmi.jpa.model.Category.class))).thenReturn(createCategoryDomain());
        when(mapper.domainToJpa(any(Category.class))).thenReturn(createCategoryJpaModel());

        when(repository.save(any())).thenReturn(createCategoryJpaModel());

        Category result = categoryServiceImpl.saveCategory(createCategoryDomain());
        assertEquals("name", result.getName());
        assertEquals("categoryID", result.getCategoryID());
    }

    @Test
    public void testSaveUpload() throws Exception {
        when(parse.streamToBean(any())).thenReturn(Arrays.<Category>asList(createCategoryDomain()));
        when(mapper.domainToJpa(any(List.class))).thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Category>asList(
            createCategoryJpaModel()));

        categoryServiceImpl.saveUpload(null);
    }

    @Test
    public void testDelete() throws Exception {
        when(repository.existsById(any())).thenReturn(true);
        categoryServiceImpl.delete("categoryId");
    }
}