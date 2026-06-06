package dev.ellesh.productservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "categories")
public class Category extends BaseModel {
    private String name;
}