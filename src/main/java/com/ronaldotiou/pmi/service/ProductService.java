package com.ronaldotiou.pmi.service;

import com.ronaldotiou.pmi.domain.Product;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(String productId);

    Product saveProduct(Product product);

    void saveUpload(MultipartFile file);

    Product updateProduct(String productId, Product product);

    void deleteProduct(String productId);

    Resource findAndDownload(String field, String value);
}
