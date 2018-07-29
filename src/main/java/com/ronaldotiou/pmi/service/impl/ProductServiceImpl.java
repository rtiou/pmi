package com.ronaldotiou.pmi.service.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ronaldotiou.pmi.domain.Product;
import com.ronaldotiou.pmi.exception.ResourceNotFoundException;
import com.ronaldotiou.pmi.jpa.repository.ProductRepository;
import com.ronaldotiou.pmi.mapper.ProductMapper;
import com.ronaldotiou.pmi.service.CategoryService;
import com.ronaldotiou.pmi.service.ParseFileProduct;
import com.ronaldotiou.pmi.service.ProductService;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ParseFileProduct parse;

    private final ProductRepository repository;

    private final CategoryService categoryService;

    private final ProductMapper mapper;

    @Override
    public List<Product> getProducts() {
        return mapper.jpaToDomain(repository.findAll());
    }

    @Override
    public Product getProduct(String productId) {
        if (repository.existsById(productId)) {
            return mapper.jpaToDomain(repository.getOne(productId));
        }
        throw new ResourceNotFoundException("ProductId " + productId + " not found");
    }

    @Override
    public Product saveProduct(Product product) {
        if (categoryService.getCategory(product.getCategoryId()) != null) {
            com.ronaldotiou.pmi.jpa.model.Product jpaProduct = mapper.domainToJpa(product);
            jpaProduct = repository.save(jpaProduct);
            return mapper.jpaToDomain(jpaProduct);
        }
        throw new ResourceNotFoundException("CategoryId " + product.getCategoryId() + " not found");
    }

    @Override
    public void saveUpload(MultipartFile multipartFile) {
        List<Product> products = parse.streamToBean(multipartFile);
        repository.saveAll(mapper.domainToJpa(products));
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        com.ronaldotiou.pmi.jpa.model.Product productJpa = mapper.domainToJpa(product);

        com.ronaldotiou.pmi.jpa.model.Product productJpaReturn =
            repository.findById(productId).map(prod -> {
                prod.setAvailable(productJpa.isAvailable());
                prod.setDescription(productJpa.getDescription());
                prod.setMinOrderQuantity(productJpa.getMinOrderQuantity());
                prod.setName(productJpa.getName());
                prod.setPurchasePrice(productJpa.getPurchasePrice());
                prod.setUnitOfMeasure(productJpa.getUnitOfMeasure());
                prod.setCategory(productJpa.getCategory());
                return repository.save(prod);
            }).orElseThrow(() -> new ResourceNotFoundException("ProductId " + productId + " not found"));

        return mapper.jpaToDomain(productJpaReturn);
    }

    @Override
    public void deleteProduct(String productId) {
        if (repository.findById(productId) != null) {
            repository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException("ProductId " + productId + " not found");
        }
    }

    @Override
    public Resource findAndDownload(String field, String value) {
        List<Product> products = retrieveProductWithParameter(field, value);

        try {
            Writer write = parse.beanToStream(products);

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Product> retrieveProductWithParameter(String field, String value) {
        List<Product> products = null;
        if ("DESCRIPTION".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByDescriptionContaining(value));
        }

        if ("AVALIABLE".equalsIgnoreCase(field)) {
            if (Boolean.valueOf(value)) {
                products = mapper.jpaToDomain(repository.findAllByAvailableIsTrue());
            } else {
                products = mapper.jpaToDomain(repository.findAllByAvailableIsFalse());
            }
        }

        if ("NAME".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByNameContaining(value));
        }

        if ("MINORDERQUANTITY".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByMinOrderQuantityEquals(new BigDecimal(value)));
        }

        if ("UNITOFMEASURE".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByUnitOfMeasureEquals(value));
        }

        if ("PURCHASEPRICE".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByPurchasePriceEquals(new BigDecimal(value)));
        }

        if ("CATEGORY".equalsIgnoreCase(field)) {
            products = mapper.jpaToDomain(repository.findAllByCategory_NameOrderByCategory(value));
        }
        return products;
    }
}
