package com.example.capstoneproject.services;

import com.example.capstoneproject.model.Product;

import java.util.List;

public interface IProductService {

    public Product getProductById(Long id);

    public Product createProduct(Product product);

    public List<Product> getAllProducts();

    public Product replaceProduct(Product product, Long id);
}
