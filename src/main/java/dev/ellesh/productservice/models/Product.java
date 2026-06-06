package dev.ellesh.productservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends  BaseModel{
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Category category;
}