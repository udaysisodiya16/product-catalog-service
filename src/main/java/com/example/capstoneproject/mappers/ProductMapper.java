package com.example.capstoneproject.mappers;

import com.example.capstoneproject.dtos.ProductDto;
import com.example.capstoneproject.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductDto> productsToProductDtos(List<Product> products);

    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product result);
}
