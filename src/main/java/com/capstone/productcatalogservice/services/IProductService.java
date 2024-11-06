package com.capstone.productcatalogservice.services;

import com.capstone.productcatalogservice.model.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product replaceProduct(Product product, Long id);

    Boolean deleteProduct(Long id);
}
