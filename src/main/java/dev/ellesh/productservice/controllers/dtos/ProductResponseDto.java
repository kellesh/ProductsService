package dev.ellesh.productservice.controllers.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private String categoryName;
}