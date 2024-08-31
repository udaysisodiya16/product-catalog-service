package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.CategoryDto;
import com.example.capstoneproject.dtos.ProductDto;
import com.example.capstoneproject.model.Category;
import com.example.capstoneproject.model.Product;
import com.example.capstoneproject.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        List<ProductDto> response = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            response.add(getProductDto(product));
        }

        return response;
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
            ProductDto productDto = getProductDto(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("called By", "Anurag Khanna");
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw exception;
        }
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = getProduct(productDto);
        Product result = productService.createProduct(product);
        return getProductDto(result);
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product input = getProduct(productDto);
        Product product = productService.replaceProduct(input, id);
        return getProductDto(product);
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            CategoryDto category = new CategoryDto();
            category.setId(product.getCategory().getId());
            category.setName(product.getCategory().getName());
            category.setDescription(product.getCategory().getDescription());
            productDto.setCategory(category);
        }
        return productDto;
    }
}
