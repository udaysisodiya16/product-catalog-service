package com.capstone.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
    private Long id;

    private String name;

    private String description;
}
