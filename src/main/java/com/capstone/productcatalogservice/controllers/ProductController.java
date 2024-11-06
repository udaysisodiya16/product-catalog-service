package com.capstone.productcatalogservice.controllers;

import com.capstone.productcatalogservice.dtos.ProductDto;
import com.capstone.productcatalogservice.mappers.ProductMapper;
import com.capstone.productcatalogservice.model.Product;
import com.capstone.productcatalogservice.services.IProductService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(productMapper.productsToProductDtos(products));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if (productId == 0) {
            throw new IllegalArgumentException("ProductId is invalid");
        } else if (productId < 0) {
            throw new IllegalArgumentException("Are you crazy ?");
        }

        Product product = productService.getProductById(productId);
        ProductDto productDto = productMapper.productToProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        Product result = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.productToProductDto(result));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product input = productMapper.productDtoToProduct(productDto);
        Product product = productService.replaceProduct(input, id);
        return ResponseEntity.ok(productMapper.productToProductDto(product));
    }
}
