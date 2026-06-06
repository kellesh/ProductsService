package dev.ellesh.productservice.repository;

import dev.ellesh.productservice.models.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCategoryRepositoryImpl implements CategoryRepository {

    private final Map<Long, Category> categoryMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryCategoryRepositoryImpl() {
        // Pre-populate with common e-commerce categories
        addCategory("Electronics");
        addCategory("Clothing");
        addCategory("Books");
        addCategory("Home & Kitchen");
        addCategory("Sports & Outdoors");
        addCategory("Beauty & Personal Care");
    }

    private void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setId(idGenerator.getAndIncrement());
        categoryMap.put(category.getId(), category);
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(idGenerator.getAndIncrement());
        }
        categoryMap.put(category.getId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(categoryMap.get(id));
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryMap.values().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryMap.values());
    }

    @Override
    public void deleteById(Long id) {
        categoryMap.remove(id);
    }
}
