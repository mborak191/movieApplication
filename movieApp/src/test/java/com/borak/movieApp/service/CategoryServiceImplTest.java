package com.borak.movieApp.service;

import com.borak.movieApp.domain.Category;
import com.borak.movieApp.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;


    @Test
    public void saveCategory() {

        Category category = new Category();
        category.setId((long) 1);
        category.setName("Comedy");

        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        assertEquals((category), categoryService.saveCategory(category));

    }

    @Test
    public void itShouldThrowNullPointerExceptionWhenCategoryNameIsNull() {

        Category category1 = new Category(null, null);
        Mockito.when(categoryRepository.save(category1)).thenThrow(NullPointerException.class);

        assertNull(categoryService.saveCategory(category1));
    }

    @Test
    public void updateCategory() {

        Category oldCategory = new Category((long) 1, "action");
        Category replacingCategory = new Category((long) 1, "thriller");

        Mockito.when(categoryRepository.findById(replacingCategory.getId())).thenReturn(Optional.of(oldCategory));
        Mockito.when(categoryRepository.save(replacingCategory)).thenReturn(replacingCategory);

        assertEquals((replacingCategory), categoryService.updateCategory(replacingCategory));
    }

    @Test
    public void updateCategoryThrowsNull() {

        Category oldCategory = new Category((long) 1, "action");
        Category replacingCategory = new Category(null, "thriller");

        Mockito.when(categoryRepository.findById(replacingCategory.getId())).thenThrow(NullPointerException.class);

        assertEquals((null), categoryService.updateCategory(replacingCategory));


    }

    @Test
    public void getCategory() {

        Category category = new Category((long) 1, "thriller");

        Mockito.when(categoryRepository.findByName(category.getName())).thenReturn(category);

        assertEquals((category), categoryService.getCategory(category.getName()));
    }

    @Test
    public void getCategories() {

        Pageable paging = PageRequest.of(1, 3);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category((long) 1, "comedy"));
        categories.add(new Category((long) 2, "action"));
        categories.add(new Category((long) 3, "horror"));

        Page<Category> pageCategory = new PageImpl<>(categories, paging, categories.size());

        Mockito.when(categoryRepository.findAll(paging)).thenReturn(pageCategory);

        assertEquals((pageCategory.toList()), categoryService.getCategories(1, 3));
    }

}
