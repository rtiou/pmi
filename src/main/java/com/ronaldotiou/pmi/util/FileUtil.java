package com.ronaldotiou.pmi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    /**
     * Method that receive a {@link MultipartFile} file and return a {@link File}
     *
     * @param file A multipartFile input
     * @return return a file
     */
    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
