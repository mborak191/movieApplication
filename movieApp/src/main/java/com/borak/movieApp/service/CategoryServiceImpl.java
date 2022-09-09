package com.borak.movieApp.service;


import com.borak.movieApp.domain.Category;
import com.borak.movieApp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        try {

            if (categoryRepository.findByName(category.getName()) == null) {
                return categoryRepository.save(category);
            }

        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
        log.info("Category {} already exists in database" + category.getName());
        return null;
    }

    @Override
    public Category updateCategory(Category category) {

        try {
            if (categoryRepository.findById(category.getId()).isPresent()) {
                return categoryRepository.save(category);
            }

        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
        return null;
    }

    @Override
    public Category getCategory(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getCategories(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Category> pageResult = categoryRepository.findAll(paging);

        return pageResult.toList();
    }
}
