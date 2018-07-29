package com.ronaldotiou.pmi.controller;

import com.ronaldotiou.pmi.domain.Product;
import com.ronaldotiou.pmi.exception.DownloadFileException;
import com.ronaldotiou.pmi.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Product Controller class.
 */
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /**
     * Endpoint to retrieve all products.
     *
     * @return return a list of products
     */
    @GetMapping
    public List<Product> getProducts() {
        return service.getProducts();
    }

    /**
     * Endpoint to retrieve a single product using the productId.
     *
     * @param productId the product id.
     * @return product
     */
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId) {
        return service.getProduct(productId);
    }

    /**
     * Endpoint to create a new product.
     *
     * @param product The new product will be create.
     * @return return the product created.
     */
    @PostMapping
    public Product postProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    /**
     * Endpoint to update an exist productId.
     *
     * @param productId the productId id used to find the productId to be updated.
     * @param product the new productId information.
     * @return the new productId.
     */
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable String productId, @Valid @RequestBody Product product) {
        return service.updateProduct(productId, product);
    }

    /**
     * Endpoint to delete a product using the productId.
     *
     * @param productId The product id used to delete.
     * @return return 200 if the product was successful deleted.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to handler the upload file with product.
     *
     * @param file The file to be process.
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        service.saveUpload(file);
        redirectAttributes
            .addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:product";
    }

    /**
     * Endpoint to download a list of product with some query.
     *
     * @param field The field to be used in the search.
     * @param value The value to be used in the search.
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadProductFile(HttpServletRequest request, @RequestParam("field") String field,
        @RequestParam("value") String value) {

        service.findAndDownload(field, value);

        Resource resource = new PathResource("ProductDownload.csv");

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException ex) {
            throw new DownloadFileException("Error to download Product file");
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);

    }
}
