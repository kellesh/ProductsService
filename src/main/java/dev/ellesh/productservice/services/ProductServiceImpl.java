package dev.ellesh.productservice.services;

import dev.ellesh.productservice.exceptions.CategoryNotFoundException;
import dev.ellesh.productservice.exceptions.ProductNotFoundException;
import dev.ellesh.productservice.models.Product;
import dev.ellesh.productservice.repository.CategoryRepository;
import dev.ellesh.productservice.repository.ProductRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import  dev.ellesh.productservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(String title, String description, BigDecimal price, String image, String categoryName) throws CategoryNotFoundException {
       return null;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, BigDecimal price, String image, String categoryName) throws ProductNotFoundException, CategoryNotFoundException {
        throw new ProductNotFoundException("No such product");
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
       throw new ProductNotFoundException("No such product");

    }

    @Override
    public void deleteProductById(Long id) throws ProductNotFoundException {
        throw new ProductNotFoundException("No such product");
    }
}