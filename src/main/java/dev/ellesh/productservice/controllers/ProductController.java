package dev.ellesh.productservice.controllers;

import dev.ellesh.productservice.controllers.dtos.CreateProductRequestDto;
import dev.ellesh.productservice.controllers.dtos.ProductResponseDto;
import dev.ellesh.productservice.controllers.dtos.UpdateProductRequestDto;
import dev.ellesh.productservice.exceptions.CategoryNotFoundException;
import dev.ellesh.productservice.exceptions.ProductNotFoundException;
import dev.ellesh.productservice.models.Product;
import dev.ellesh.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the given details")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody CreateProductRequestDto requestDto) throws CategoryNotFoundException {
        Product product = productService.createProduct(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getImage(),
                requestDto.getCategoryName()
        );
        return new ResponseEntity<>(mapToDto(product), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
    public ResponseEntity<ProductResponseDto> getProductByID(@PathVariable("id") String id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(mapToDto(product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", description = "Updates an existing product with the given details")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable("id") String id, @Valid @RequestBody UpdateProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = productService.updateProduct(
                id,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getImage(),
                requestDto.getCategoryName()
        );
        return new ResponseEntity<>(mapToDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProductByID(@PathVariable("id") String id) throws ProductNotFoundException {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ProductResponseDto mapToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}