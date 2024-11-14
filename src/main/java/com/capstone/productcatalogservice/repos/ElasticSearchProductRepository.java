package com.capstone.productcatalogservice.repos;

import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticSearchProductRepository extends ElasticsearchRepository<ElasticSearchProduct, String> {

    // Custom query method to search products by name or description
    List<ElasticSearchProduct> findByNameContainingOrDescriptionContaining(String name, String description);
}
