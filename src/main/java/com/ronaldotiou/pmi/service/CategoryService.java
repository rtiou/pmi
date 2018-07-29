package com.ronaldotiou.pmi.service;

import com.ronaldotiou.pmi.domain.Category;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    List<Category> getCategories();

    Category getCategory(String categoryId);

    Category saveCategory(Category category);

    void saveUpload(MultipartFile file);

    void delete(String categoryId);

    Category update(String categoryId, Category category);
}
