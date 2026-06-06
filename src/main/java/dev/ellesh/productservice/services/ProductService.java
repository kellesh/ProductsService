package dev.ellesh.productservice.service;

import dev.ellesh.productservice.exceptions.ProductNotFoundException;
import dev.ellesh.productservice.exceptions.CategoryNotFoundException;
import dev.ellesh.productservice.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(String title, String description, BigDecimal price, String image, String categoryName) throws CategoryNotFoundException;
    Product updateProduct(String id, String title, String description, BigDecimal price, String image, String categoryName) throws ProductNotFoundException, CategoryNotFoundException;
    List<Product> getAllProducts();
    Product getProductById(String id) throws ProductNotFoundException;
    void deleteProductById(String id) throws ProductNotFoundException;
}