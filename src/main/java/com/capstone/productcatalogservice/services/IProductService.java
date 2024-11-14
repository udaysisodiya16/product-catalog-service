package com.capstone.productcatalogservice.services;

import com.capstone.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Product getProduct(Long id);

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product replaceProduct(Product product, Long id);

    Boolean deleteProduct(Long id);

    Page<Product> searchProducts(String searchKey, Integer pageNo, Integer pageSize, String sortBy,
                                 String sortOrder, String category);
}
