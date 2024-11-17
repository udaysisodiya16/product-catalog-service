package com.capstone.productcatalogservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String specifications;

    @NotNull
    private Double price;

    private String imageUrl;

    @NotNull
    private CategoryDto category;
}
