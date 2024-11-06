package com.capstone.productcatalogservice.services;

import com.capstone.productcatalogservice.model.Category;
import com.capstone.productcatalogservice.model.Product;
import com.capstone.productcatalogservice.repos.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StorageProductServiceTest {

    @Autowired
    private IProductService productService;

    @MockBean
    private ProductRepo productRepo;

    @Test
    public void Test_GetProductById() {
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
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        //Act
        Product productActual = productService.getProductById(productId);

        //Assert
        assertNotNull(productActual);
        assertEquals(productActual.getId(), productId);
        assertEquals("Iphone12", productActual.getName() );
        verify(productRepo, times(1)).findById(productId);
    }

    @Test
    void createProduct() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void replaceProduct() {
    }
}