package com.capstone.productcatalogservice.controllers;

import com.capstone.productcatalogservice.dtos.ProductDto;
import com.capstone.productcatalogservice.dtos.ProductSearchResponse;
import com.capstone.productcatalogservice.mappers.ProductMapper;
import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import com.capstone.productcatalogservice.models.Product;
import com.capstone.productcatalogservice.services.IProductService;
import jakarta.validation.constraints.NotBlank;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(productMapper.productsToProductDtos(products));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<ProductDto>> getAllProductsByIds(@RequestParam List<Long> productIds) {
        List<Product> products = productService.getAllProductsByIds(productIds);
        return ResponseEntity.ok(productMapper.productsToProductDtos(products));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        if (productId == 0) {
            throw new IllegalArgumentException("ProductId is invalid");
        } else if (productId < 0) {
            throw new IllegalArgumentException("Are you crazy ?");
        }
        Product product = productService.getProduct(productId);
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

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        Boolean status = productService.deleteProduct(id);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<Page<ProductDto>> searchProductsByCategory(@RequestParam(required = false) String searchKey,
                                                                     @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                                                     @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                                     @RequestParam(defaultValue = "name", required = false) String sortBy,
                                                                     @RequestParam(defaultValue = "asc", required = false) String sortOrder,
                                                                     @RequestParam @NotBlank String category) {
        Page<Product> productPage = productService.searchProductsByCategory(searchKey, pageNo, pageSize, sortBy, sortOrder, category);
        return ResponseEntity.ok(productPage.map(productMapper::productToProductDto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductSearchResponse>> searchProducts(@RequestParam String searchKey,
                                                                      @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                                                      @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                                      @RequestParam(defaultValue = "name", required = false) String sortBy,
                                                                      @RequestParam(defaultValue = "asc", required = false) String sortOrder) {
        Page<ElasticSearchProduct> elasticSearchProductPage = productService.searchProducts(searchKey, pageNo, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(elasticSearchProductPage.map(productMapper::elasticSearchProductToProductSearchResponse));
    }

}
