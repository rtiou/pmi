package com.ronaldotiou.pmi.jpa.repository;

import com.ronaldotiou.pmi.jpa.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByAvailableIsFalse();

    List<Product> findAllByAvailableIsTrue();

    List<Product> findAllByNameContaining(String name);

    List<Product> findAllByDescriptionContaining(String description);

    List<Product> findAllByMinOrderQuantityEquals(BigDecimal minOrderQuantityEquals);

    List<Product> findAllByUnitOfMeasureEquals(String unitOfMeasureEquals);

    List<Product> findAllByPurchasePriceEquals(BigDecimal purchasePrice);

    List<Product> findAllByCategory_NameOrderByCategory(String categoryName);


}
