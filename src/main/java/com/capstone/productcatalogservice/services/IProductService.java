package com.capstone.productcatalogservice.services;

import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import com.capstone.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Product getProduct(Long id);

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product replaceProduct(Product product, Long id);

    Boolean deleteProduct(Long id);

    Page<Product> searchProductsByCategory(String searchKey, Integer pageNo, Integer pageSize, String sortOrder, String category);

    Page<ElasticSearchProduct> searchProducts(String searchKey, Integer pageNo, Integer pageSize, String sortOrder);
}
