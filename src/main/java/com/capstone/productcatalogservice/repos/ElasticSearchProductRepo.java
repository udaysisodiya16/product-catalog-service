package com.capstone.productcatalogservice.repos;

import com.capstone.productcatalogservice.models.ElasticSearchProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchProductRepo extends ElasticsearchRepository<ElasticSearchProduct, String> {

    // Custom query method to search products by name or description
    Page<ElasticSearchProduct> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    // Fuzzy search for name or description
    @Query("{ \"multi_match\": { \"query\": \"?0\", \"fields\": [\"name\", \"description\"], \"fuzziness\": \"AUTO\" } }")
    Page<ElasticSearchProduct> searchProductsWithFuzziness(String searchKey, Pageable pageable);
}
