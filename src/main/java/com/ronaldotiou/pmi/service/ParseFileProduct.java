package com.ronaldotiou.pmi.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ronaldotiou.pmi.domain.Product;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ParseFileProduct {

    List<Product> streamToBean(MultipartFile multipartFile);

    Writer beanToStream(List<Product> productList)
        throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
