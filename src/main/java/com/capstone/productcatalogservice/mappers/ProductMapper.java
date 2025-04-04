package com.capstone.productcatalogservice.mappers;

import com.capstone.productcatalogservice.dtos.ProductDto;
import com.capstone.productcatalogservice.dtos.ProductSearchResponse;
import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import com.capstone.productcatalogservice.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductDto> productsToProductDtos(List<Product> products);

    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product result);

    ProductDto elasticSearchProductToProductDto(ElasticSearchProduct elasticSearchProduct);

    ElasticSearchProduct productToElasticSearchProduct(Product savedProduct);

    ProductSearchResponse elasticSearchProductToProductSearchResponse(ElasticSearchProduct elasticSearchProduct);
}
