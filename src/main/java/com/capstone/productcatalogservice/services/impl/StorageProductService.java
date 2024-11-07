package com.capstone.productcatalogservice.services.impl;

import com.capstone.productcatalogservice.models.Product;
import com.capstone.productcatalogservice.repos.ProductRepo;
import com.capstone.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProduct(Long id) {
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

    @Override
    public Boolean deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        productRepo.delete(product);
        return true;
    }

    @Override
    public Page<Product> searchProducts(String searchKey, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
    }
}
