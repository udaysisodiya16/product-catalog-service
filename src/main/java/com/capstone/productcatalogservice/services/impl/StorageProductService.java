package com.capstone.productcatalogservice.services.impl;

import com.capstone.productcatalogservice.mappers.ProductMapper;
import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import com.capstone.productcatalogservice.models.Product;
import com.capstone.productcatalogservice.repos.ElasticSearchProductRepo;
import com.capstone.productcatalogservice.repos.ProductRepo;
import com.capstone.productcatalogservice.services.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ElasticSearchProductRepo elasticSearchProductRepo;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productRepo.save(product);
        ElasticSearchProduct elasticSearchProduct = productMapper.productToElasticSearchProduct(savedProduct);
        elasticSearchProductRepo.save(elasticSearchProduct);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getAllProductsByIds(List<Long> productIds) {
        return productRepo.findAllById(productIds);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        Product savedProduct = productRepo.save(product);
        ElasticSearchProduct elasticSearchProduct = productMapper.productToElasticSearchProduct(savedProduct);
        elasticSearchProductRepo.save(elasticSearchProduct);
        return savedProduct;
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Long productId = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id")).getId();
        // Delete from MySQL
        productRepo.deleteById(productId);
        // Delete from Elasticsearch
        elasticSearchProductRepo.deleteById(productId.toString());
        return true;
    }

    @Override
    public Page<Product> searchProducts(String searchKey, Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String category) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        if (category != null) {
            if (searchKey == null) {
                return productRepo.findAllByCategory_Name(category, pageable);
            } else {
                return productRepo.findByCategory_NameAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(category, searchKey, searchKey, pageable);
            }
        } else {
            if (searchKey == null) {
                return productRepo.findAll(pageable);
            } else {
                return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
            }
        }
    }

    @Override
    public Page<ElasticSearchProduct> searchProductsWithFuzziness(String searchKey, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return elasticSearchProductRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
    }
}
