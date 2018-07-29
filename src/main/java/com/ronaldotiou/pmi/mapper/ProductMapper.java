package com.ronaldotiou.pmi.mapper;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.domain.Product;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
        @Mapping(target = "available", ignore = true),
        @Mapping(target = "categoryId", source = "category.id")
    })
    Product jpaToDomain(com.ronaldotiou.pmi.jpa.model.Product input);

    List<Product> jpaToDomain(List<com.ronaldotiou.pmi.jpa.model.Product> input);

    @Mappings({
        @Mapping(target = "available", ignore = true),
        @Mapping(target = "category.id", source = "categoryId")
    })
    com.ronaldotiou.pmi.jpa.model.Product domainToJpa(Product input);

    List<com.ronaldotiou.pmi.jpa.model.Product> domainToJpa(List<Product> input);

    @Mapping(source = "id", target = "categoryID")
    Category jpaToDomain(com.ronaldotiou.pmi.jpa.model.Category input);

    @Mappings({
        @Mapping(source = "categoryID", target = "id"),
        @Mapping(target = "product", ignore = true)
    })
    com.ronaldotiou.pmi.jpa.model.Category jpaToDomain(Category input);

    @AfterMapping
    default void afterMapping(@MappingTarget com.ronaldotiou.pmi.jpa.model.Product target, Product input) {
        if (input.getAvailable() == 1) {
            target.setAvailable(true);
        } else {
            target.setAvailable(false);
        }
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Product target, com.ronaldotiou.pmi.jpa.model.Product source) {
        if (source.isAvailable()) {
            target.setAvailable(1);
        } else {
            target.setAvailable(0);
        }
    }
}
