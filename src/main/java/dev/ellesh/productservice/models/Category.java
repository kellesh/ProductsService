package dev.ellesh.productservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseModel {
    private String name;
}