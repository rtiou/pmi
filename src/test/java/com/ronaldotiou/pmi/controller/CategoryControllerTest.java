package com.ronaldotiou.pmi.controller;

import static com.ronaldotiou.pmi.service.helper.TestHelper.createCategoryDomain;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ronaldotiou.pmi.PmiApplication;
import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.service.CategoryService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PmiApplication.class)
@WebAppConfiguration
public class CategoryControllerTest {

    @Mock
    CategoryService service;

    @InjectMocks
    CategoryController categoryController;

    @Test
    public void testGetAllCategories() throws Exception {
        when(service.getCategories()).thenReturn(Arrays.<Category>asList(createCategoryDomain()));

        List<Category> result = categoryController.getAllCategories();
        Assert.assertEquals(Arrays.<Category>asList(createCategoryDomain()), result);
    }

    @Test
    public void testGetCategory() throws Exception {
        when(service.getCategory(any())).thenReturn(createCategoryDomain());

        Category result = categoryController.getCategory("categoryId");
        Assert.assertEquals(createCategoryDomain(), result);
    }

    @Test
    public void testPostCategory() throws Exception {
        when(service.saveCategory(any())).thenReturn(createCategoryDomain());

        Category result = categoryController.postCategory(createCategoryDomain());
        Assert.assertEquals(createCategoryDomain(), result);
    }

    @Test
    public void testDeleteCategory() throws Exception {
        ResponseEntity<?> result = categoryController.deleteCategory("categoryId");
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.OK), result);
    }

    @Test
    public void testUpdateCategory() throws Exception {
        when(service.update(any(), any())).thenReturn(createCategoryDomain());

        Category result = categoryController.updateCategory("categoryId", createCategoryDomain());
        Assert.assertEquals(createCategoryDomain(), result);
    }
}
