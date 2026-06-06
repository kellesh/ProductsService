package dev.ellesh.productservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "products")
public class Product extends  BaseModel{
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Category category;
}