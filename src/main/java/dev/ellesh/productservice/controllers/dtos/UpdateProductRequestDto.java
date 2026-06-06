package dev.ellesh.productservice.controllers.dtos;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequestDto {
    private String title;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    private String image;

    private String categoryName;
}
