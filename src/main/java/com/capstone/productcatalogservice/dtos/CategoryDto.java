package com.capstone.productcatalogservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;
}
