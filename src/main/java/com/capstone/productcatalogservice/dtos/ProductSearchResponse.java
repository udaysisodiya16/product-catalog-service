package com.capstone.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchResponse {

    private Long id;

    private String name;

    private String description;
}
