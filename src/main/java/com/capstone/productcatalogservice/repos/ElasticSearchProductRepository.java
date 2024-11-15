package com.capstone.productcatalogservice.repos;

import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchProductRepository extends ElasticsearchRepository<ElasticSearchProduct, String> {

    // Custom query method to search products by name or description
    Page<ElasticSearchProduct> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}
