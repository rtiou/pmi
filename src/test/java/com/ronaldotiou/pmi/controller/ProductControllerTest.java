package com.ronaldotiou.pmi.controller;

import static com.ronaldotiou.pmi.service.helper.TestHelper.createProductDomain;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ronaldotiou.pmi.PmiApplication;
import com.ronaldotiou.pmi.domain.Product;
import com.ronaldotiou.pmi.service.ProductService;
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
public class ProductControllerTest {

    @Mock
    ProductService service;

    @InjectMocks
    ProductController productController;

    @Test
    public void testGetProducts() throws Exception {
        when(service.getProducts()).thenReturn(Arrays.<Product>asList(createProductDomain()));

        List<Product> result = productController.getProducts();
        Assert.assertEquals(Arrays.<Product>asList(createProductDomain()), result);
    }

    @Test
    public void testGetProduct() throws Exception {
        when(service.getProduct(any())).thenReturn(createProductDomain());

        Product result = productController.getProduct("productId");
        Assert.assertEquals(createProductDomain(), result);
    }

    @Test
    public void testPostProduct() throws Exception {
        when(service.saveProduct(any())).thenReturn(createProductDomain());

        Product result = productController.postProduct(createProductDomain());
        Assert.assertEquals(createProductDomain(), result);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        when(service.updateProduct(any(), any())).thenReturn(createProductDomain());

        Product result = productController.updateProduct("productId", createProductDomain());
        Assert.assertEquals(createProductDomain(), result);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        ResponseEntity<?> result = productController.deleteProduct("productId");
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.OK), result);
    }
}