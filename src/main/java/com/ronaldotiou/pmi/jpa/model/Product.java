package com.ronaldotiou.pmi.jpa.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String zamroID;

    private String name;

    private String description;

    private BigDecimal minOrderQuantity;

    private String unitOfMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private com.ronaldotiou.pmi.jpa.model.Category category;

    private BigDecimal purchasePrice;

    private boolean available;

}
