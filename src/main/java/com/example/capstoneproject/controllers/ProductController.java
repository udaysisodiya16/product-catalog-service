package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.ProductDto;
import com.example.capstoneproject.mappers.ProductMapper;
import com.example.capstoneproject.model.Product;
import com.example.capstoneproject.services.IProductService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        return productMapper.productsToProductDtos(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            if (productId == 0) {
                throw new IllegalArgumentException("ProductId is invalid");
            } else if (productId < 0) {
                throw new IllegalArgumentException("Are you crazy ?");
            }

            Product product = productService.getProductById(productId);
            ProductDto productDto = productMapper.productToProductDto(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("called By", "Anurag Khanna");
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw exception;
        }
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        Product result = productService.createProduct(product);
        return productMapper.productToProductDto(result);
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product input = productMapper.productDtoToProduct(productDto);
        Product product = productService.replaceProduct(input, id);
        return productMapper.productToProductDto(product);
    }
}
