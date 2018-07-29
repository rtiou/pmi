package com.ronaldotiou.pmi.service.impl;

import static com.ronaldotiou.pmi.util.FileUtil.convertMultiPartToFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ronaldotiou.pmi.domain.Product;
import com.ronaldotiou.pmi.service.ParseFileProduct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductOpenCsvParse implements ParseFileProduct {

    @Override
    public List<Product> streamToBean(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            multipartFile.transferTo(file);

            RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();

            CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(rfc4180Parser);

            CSVReader reader = csvReaderBuilder.build();

            return transformer(reader.readAll());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Writer beanToStream(List<Product> productList)
        throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter("ProductDownload.csv");
        StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(productList);
        writer.close();
        return writer;
    }


    private List<Product> transformer(List<String[]> input) {
        if (input != null) {
            input.remove(0);
            List<Product> productList = new ArrayList<>();
            for (String[] product : input) {
                productList.add(
                    new Product(
                        product[0],
                        product[1],
                        product[2],
                        StringUtils.isEmpty(product[3]) ? BigDecimal.ZERO : new BigDecimal(product[3]),
                        product[4],
                        product[5],
                        new BigDecimal(product[6]),
                        Integer.parseInt(product[7])));
            }
            return productList;
        }
        return null;
    }
}
