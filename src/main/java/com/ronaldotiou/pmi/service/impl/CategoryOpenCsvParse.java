package com.ronaldotiou.pmi.service.impl;

import static com.ronaldotiou.pmi.util.FileUtil.convertMultiPartToFile;

import com.opencsv.bean.CsvToBeanBuilder;
import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.exception.UploadFileException;
import com.ronaldotiou.pmi.service.ParseFileCategory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryOpenCsvParse implements ParseFileCategory {

    @Override
    public List<Category> streamToBean(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);

            Reader reader = new FileReader(file);

            return new CsvToBeanBuilder<Category>(reader)
                .withType(Category.class)
                .build()
                .parse();

        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadFileException("Error to processing Category File: " + multipartFile.getOriginalFilename());
        }
    }
}
