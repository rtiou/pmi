package com.ronaldotiou.pmi.jpa.repository;

import com.ronaldotiou.pmi.jpa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
