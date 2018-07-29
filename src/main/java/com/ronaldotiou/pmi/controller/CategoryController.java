package com.ronaldotiou.pmi.controller;

import com.ronaldotiou.pmi.domain.Category;
import com.ronaldotiou.pmi.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
 * Category Controller class.
 */
@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    /**
     * Endpoint to retrieve all categories.
     *
     * @return return a list of categories
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return service.getCategories();
    }

    /**
     * Endpoint to retrieve a single category using the categoryId.
     *
     * @param categoryId the category id.
     * @return category
     */
    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable(value = "categoryId") String categoryId) {
        return service.getCategory(categoryId);
    }

    /**
     * Endpoint to create a new category.
     *
     * @param category The new category will be create.
     * @return return the category created.
     */
    @PostMapping
    public Category postCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    /**
     * Endpoint to delete a category using the categoryId.
     *
     * @param categoryId The category id used to delete.
     * @return return 200 if the category was successful deleted.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "categoryId") String categoryId) {
        service.delete(categoryId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to update an exist category.
     *
     * @param categoryId the category id used to find the category to be updated.
     * @param category the new category information.
     * @return the new category.
     */
    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable(value = "categoryId") String categoryId,
        @Valid @RequestBody Category category) {
        return service.update(categoryId, category);
    }

    /**
     * Endpoint to handler the upload file with category.
     *
     * @param file The file to be process.
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        service.saveUpload(file);
        redirectAttributes
            .addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:category";
    }
}
