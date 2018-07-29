package com.ronaldotiou.pmi.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String zamroID;
    private String name;
    private String description;
    private BigDecimal minOrderQuantity;
    private String unitOfMeasure;
    private String categoryId;
    private BigDecimal purchasePrice;
    private int available;
}
