package dev.ellesh.productservice.repository;

import dev.ellesh.productservice.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
    void deleteById(Long id);

}
