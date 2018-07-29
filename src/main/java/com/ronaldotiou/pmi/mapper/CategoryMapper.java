package com.ronaldotiou.pmi.mapper;

import com.ronaldotiou.pmi.domain.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "id", target = "categoryID")
    Category jpaToDomain(com.ronaldotiou.pmi.jpa.model.Category input);

    List<Category> jpaToDomain(List<com.ronaldotiou.pmi.jpa.model.Category> input);

    @Mapping(source = "categoryID", target = "id")
    com.ronaldotiou.pmi.jpa.model.Category domainToJpa(Category input);

    List<com.ronaldotiou.pmi.jpa.model.Category> domainToJpa(List<Category> input);
}
