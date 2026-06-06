package dev.ellesh.productservice.controllers.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequestDto {
    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    private String image;

    @NotBlank(message = "Category name is mandatory")
    private String categoryName;
}
