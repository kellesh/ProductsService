package dev.ellesh.productservice;

import dev.ellesh.productservice.exceptions.CategoryNotFoundException;
import dev.ellesh.productservice.exceptions.ProductNotFoundException;
import dev.ellesh.productservice.models.Category;
import dev.ellesh.productservice.models.Product;
import dev.ellesh.productservice.repository.CategoryRepository;
import dev.ellesh.productservice.repository.ProductRepository;
import dev.ellesh.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductsServiceApplicationTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private dev.ellesh.productservice.services.ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() throws CategoryNotFoundException {
        Category category = new Category();
        category.setName("Test Category");
        when(categoryRepository.findByName("Test Category")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product product = productService.createProduct("Test Product", "Description", new BigDecimal("10.00"), "image.jpg", "Test Category");

        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals("Test Category", product.getCategory().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProductWithNewCategory() throws CategoryNotFoundException {
        when(categoryRepository.findByName("New Category")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product product = productService.createProduct("Test Product", "Description", new BigDecimal("10.00"), "image.jpg", "New Category");

        assertNotNull(product);
        assertEquals("New Category", product.getCategory().getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product();
        p1.setTitle("Product 1");
        Product p2 = new Product();
        p2.setTitle("Product 2");
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct() throws ProductNotFoundException, CategoryNotFoundException {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Old Title");
        Category category = new Category();
        category.setName("Old Category");
        existingProduct.setCategory(category);

        Category newCategory = new Category();
        newCategory.setName("New Category");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("New Category")).thenReturn(Optional.of(newCategory));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = productService.updateProduct(1L, "New Title", "New Desc", new BigDecimal("20.00"), "new.jpg", "New Category");

        assertEquals("New Title", updatedProduct.getTitle());
        assertEquals("New Desc", updatedProduct.getDescription());
        assertEquals("New Category", updatedProduct.getCategory().getName());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, "T", "D", BigDecimal.ONE, "I", "C"));
    }

    @Test
    void testUpdateProductCategoryNotFound() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("Non-existent Category")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(1L, null, null, null, null, "Non-existent Category"));
    }

    @Test
    void testDeleteProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1L));
    }
}