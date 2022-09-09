package com.borak.movieApp.service;

import com.borak.movieApp.domain.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    Category getCategory(String name);

    List<Category> getCategories(int pageNo, int pageSize);
}
