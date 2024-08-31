package com.example.capstoneproject.services.impl;

import com.example.capstoneproject.model.Product;
import com.example.capstoneproject.repos.ProductRepo;
import com.example.capstoneproject.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
       return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        return productRepo.save(product);
    }
}
