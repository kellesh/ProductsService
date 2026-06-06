package dev.ellesh.productservice.services;

import dev.ellesh.productservice.exceptions.CategoryNotFoundException;
import dev.ellesh.productservice.exceptions.ProductNotFoundException;
import dev.ellesh.productservice.models.Category;
import dev.ellesh.productservice.models.Product;
import dev.ellesh.productservice.repository.CategoryRepository;
import dev.ellesh.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import dev.ellesh.productservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(String title, String description, BigDecimal price, String image, String categoryName) throws CategoryNotFoundException {
        Category category = categoryRepository.findByName(categoryName).orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            return categoryRepository.save(newCategory);
        });

        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, String title, String description, BigDecimal price, String image, String categoryName) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        if (title != null) {
            product.setTitle(title);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (image != null) {
            product.setImage(image);
        }
        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new CategoryNotFoundException("Category with name " + categoryName + " not found"));
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public void deleteProductById(Long id) throws ProductNotFoundException {
        if (!productRepository.findById(id).isPresent()) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}