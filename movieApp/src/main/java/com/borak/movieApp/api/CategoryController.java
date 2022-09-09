package com.borak.movieApp.api;

import com.borak.movieApp.domain.Category;
import com.borak.movieApp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get/{pageNo}/{pageSize}")
    public ResponseEntity<List<Category>> getCategories(@PathVariable int pageNo, @PathVariable int pageSize) {

        return ResponseEntity.ok().body(categoryService.getCategories(pageNo, pageSize));
    }

    @GetMapping("/get/{categoryName}")
    public ResponseEntity<Category> getCategory(@PathVariable String categoryName) {

        return ResponseEntity.ok().body(categoryService.getCategory(categoryName));
    }

    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/category/save").toUriString());
        return ResponseEntity.created(uri).body(categoryService.saveCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateMovie(@RequestBody Category category) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/category/update").toUriString());
        return ResponseEntity.created(uri).body(categoryService.updateCategory(category));
    }
}
