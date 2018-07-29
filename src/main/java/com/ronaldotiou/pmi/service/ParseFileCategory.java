package com.ronaldotiou.pmi.service;

import com.ronaldotiou.pmi.domain.Category;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ParseFileCategory {

    List<Category> streamToBean(MultipartFile multipartFile);
}
