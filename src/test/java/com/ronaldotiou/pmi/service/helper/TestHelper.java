package com.ronaldotiou.pmi.service.helper;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.domain.Product;
import java.math.BigDecimal;

public class TestHelper {

    /**
     * Create a Product Domain Object to be used in the tests.
     *
     * @return an instance of Product Domain
     */
    public static Product createProductDomain() {
        return new Product("zamroID", "Spanplaten DIN 6316 11x100mm", "description",
            new BigDecimal(0), "unitOfMeasure", "categoryId", new BigDecimal(0), 1);
    }

    /**
     * Create a Product Jpa Domain Object to be used in the tests.
     *
     * @return an instance of Product Jpa Domain
     */
    public static com.ronaldotiou.pmi.jpa.model.Product createProductJpaModel() {
        return new com.ronaldotiou.pmi.jpa.model.Product("zamroID", "name", "description",
            new BigDecimal(0), "unitOfMeasure", null, new BigDecimal(0), true);
    }

    /**
     * Create a Category Domain Object to be used in the tests.
     *
     * @return an instance of Category Domain
     */
    public static Category createCategoryDomain() {
        return new Category("categoryID", "name");
    }

    /**
     * Create a Category Jpa Domain Object to be used in the tests.
     *
     * @return an instance of Category Jpa Domain
     */
    public static com.ronaldotiou.pmi.jpa.model.Category createCategoryJpaModel() {
        return new com.ronaldotiou.pmi.jpa.model.Category("48622", "Decoupeerzagen en recipro zaagbladen", null);
    }

}
