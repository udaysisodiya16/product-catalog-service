package com.capstone.productcatalogservice.controllers;

import com.capstone.productcatalogservice.dtos.ProductDto;
import com.capstone.productcatalogservice.mappers.ProductMapper;
import com.capstone.productcatalogservice.model.Product;
import com.capstone.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    //object <-> json <-> string
    @Autowired
    private ObjectMapper objectMapper;

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void Test_GetAllProductsAPI_TestsStatusOnly() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus() throws Exception {

        //Arrange
        Product product1 = new Product();
        product1.setName("Iphone12");
        Product product2 = new Product();
        product2.setName("Iphone15");
        List<Product> productList = Arrays.asList(product1, product2);
        when(productService.getAllProducts()).thenReturn(productList);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productMapper.productsToProductDtos(productList))));
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus_AssertInJson() throws Exception {

        //Arrange
        Product product1 = new Product();
        product1.setName("Iphone12");
        Product product2 = new Product();
        product2.setName("Iphone15");
        List<Product> productList = Arrays.asList(product1, product2);
        when(productService.getAllProducts()).thenReturn(productList);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productMapper.productsToProductDtos(productList))))
                .andExpect(jsonPath("$[0].name").value("Iphone12"))
                .andExpect(jsonPath("$[1].name").value("Iphone15"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void Test_CreateProduct_ProductCreatedSuccessfully() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("MacBook");
        productDto.setId(10L);

        Product product = new Product();
        product.setName("MacBook");
        product.setId(10L);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));

    }

    @Test
    public void Test_CreateProduct_ProductCreatedSuccessfully_AssertInJsons() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("MacBook");
        productDto.setId(10L);

        when(productService.createProduct(any(Product.class))).thenReturn(productMapper.productDtoToProduct(productDto));

        //Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.name").value("MacBook"))
                .andExpect(jsonPath("$.id").value(10));
    }
}