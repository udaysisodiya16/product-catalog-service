package com.capstone.productcatalogservice.controllers;

import com.capstone.productcatalogservice.dtos.ProductDto;
import com.capstone.productcatalogservice.models.Category;
import com.capstone.productcatalogservice.models.Product;
import com.capstone.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    public void Test_GetProductById_WithValidId_ReturnsProductSuccessfully() {
        //Arrange
        Long productId = 9999999999L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone12");
        product.setPrice(100000D);
        Category category = new Category();
        category.setId(2L);
        category.setName("iPHONES");
        product.setCategory(category);
        when(productService.getProduct(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> response = productController.getProduct(productId);

        //Assert
        assertNotNull(response);
        assertEquals("Iphone12", response.getBody().getName() );
        assertEquals(response.getBody().getId(), productId);
        verify(productService, times(1)).getProduct(productId);
    }

    @Test
    public void Test_GetProduct_ThrowsIllegalArgumentException() {
        //Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productController.getProduct(-1L));

        assertEquals("Are you crazy ?", exception.getMessage());

        verify(productService, times(0)).getProduct(-1L);
    }

    @Test
    public void Test_GetProductById_ProductServiceThrowsException() {
        //Arrange
        Long productId = 2L;
        when(productService.getProduct(productId)).
                thenThrow(new RuntimeException("something went bad !!"));

        assertThrows(Exception.class, () -> productController.getProduct(productId));
    }

    @Test
    public void Test_CreateProduct_ValidPayload_RunsSuccessfully() {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Iphone12");
        productDto.setPrice(100000D);

        Product product = new Product();
        product.setId(1L);
        product.setName("Iphone12");
        product.setPrice(100000D);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> responseEntity = productController.createProduct(productDto);

        //Assert
        assertNotNull(responseEntity.getBody());
        assertEquals("Iphone12", responseEntity.getBody().getName());
    }

    @DisplayName("Passing product id as 1 to controller and expect same on product service call as well, if this assert fails, that means value was not 1")
    @Test
    public void Test_GetProduct_ServiceCalledWithExpectedArguments_RunSuccessfully() {
        //Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productService.getProduct(any(Long.class))).thenReturn(product);

        //Act
        productController.getProduct(productId);

        //Assert
        verify(productService).getProduct(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
    }
}