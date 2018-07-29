package com.ronaldotiou.pmi.service.impl;

import static com.ronaldotiou.pmi.service.helper.TestHelper.createProductDomain;
import static com.ronaldotiou.pmi.service.helper.TestHelper.createProductJpaModel;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.domain.Product;
import com.ronaldotiou.pmi.jpa.repository.ProductRepository;
import com.ronaldotiou.pmi.mapper.ProductMapper;
import com.ronaldotiou.pmi.service.CategoryService;
import com.ronaldotiou.pmi.service.ParseFileProduct;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

    @Mock
    ParseFileProduct parse;

    @Mock
    ProductRepository repository;

    @Mock
    CategoryService categoryService;

    @Mock
    ProductMapper mapper;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() {
    }

    @Test
    public void testGetProducts() throws Exception {
        when(mapper.jpaToDomain(any(List.class))).thenReturn(Arrays.<Product>asList(createProductDomain()));

        List<Product> result = productServiceImpl.getProducts();
        Product product = result.get(0);

        assertEquals("zamroID", product.getZamroID());
        assertEquals("Spanplaten DIN 6316 11x100mm", product.getName());
        assertEquals("description", product.getDescription());

    }

    @Test
    public void testGetProduct() throws Exception {
        when(categoryService.getCategory(any())).thenReturn(new Category("categoryID", "name"));

        when(repository.getOne(any())).thenReturn(createProductJpaModel());

        when(repository.existsById(any())).thenReturn(true);

        when(mapper.jpaToDomain(any(com.ronaldotiou.pmi.jpa.model.Product.class))).thenReturn(createProductDomain());

        Product result = productServiceImpl.getProduct("productId");

        assertEquals("categoryId", result.getCategoryId());
        assertEquals("zamroID", result.getZamroID());
    }

    @Test
    public void testSaveProduct() throws Exception {
        when(categoryService.getCategory(any())).thenReturn(new Category("categoryID", "name"));

        when(repository.save(any())).thenReturn(createProductJpaModel());

        when(mapper.jpaToDomain(any(com.ronaldotiou.pmi.jpa.model.Product.class))).thenReturn(createProductDomain());

        when(mapper.domainToJpa(any(Product.class))).thenReturn(createProductJpaModel());

        Product result = productServiceImpl.saveProduct(createProductDomain());

        assertEquals("zamroID", result.getZamroID());
        assertEquals("Spanplaten DIN 6316 11x100mm", result.getName());
    }

    @Test
    public void testSaveUpload() throws Exception {
        when(parse.streamToBean(any())).thenReturn(Arrays.<Product>asList(createProductDomain()));

        when(mapper.domainToJpa(any(List.class))).thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(
            createProductJpaModel()));

        productServiceImpl.saveUpload(null);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        productServiceImpl.deleteProduct("productId");
    }

    @Test
    public void testFindAndDownload() throws Exception {
        when(parse.beanToStream(any())).thenReturn(null);
        when(repository.findAllByAvailableIsFalse())
            .thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(createProductJpaModel()));
        when(repository.findAllByAvailableIsTrue()).thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(
            createProductJpaModel()));
        when(repository.findAllByNameContaining(any())).thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(
            createProductJpaModel()));
        when(repository.findAllByDescriptionContaining(any()))
            .thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(
                createProductJpaModel()));
        when(repository.findAllByMinOrderQuantityEquals(any())).thenReturn(
            Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(createProductJpaModel()));
        when(repository.findAllByUnitOfMeasureEquals(any()))
            .thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(createProductJpaModel()));
        when(repository.findAllByPurchasePriceEquals(any()))
            .thenReturn(Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(createProductJpaModel()));
        when(repository.findAllByCategory_NameOrderByCategory(any())).thenReturn(
            Arrays.<com.ronaldotiou.pmi.jpa.model.Product>asList(createProductJpaModel()));

        when(mapper.jpaToDomain(any(List.class))).thenReturn(Arrays.<Product>asList(createProductDomain()));

        Resource result = productServiceImpl.findAndDownload("field", "value");
        assertEquals(null, result);
    }
}
