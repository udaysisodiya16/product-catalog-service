package com.capstone.productcatalogservice.repos;

import com.capstone.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findProductByPriceBetween(Double low, Double high);

    List<Product> findAllByOrderByPriceDesc();

    @Query("SELECT p.name from Product p WHERE p.id=?1")
    String findProductNameFromProductId(Long productId);

    @Query("SELECT c.name FROM Category c join Product p on c.id=p.category.id where p.id=:productId")
    String findCategoryNameFromProductId(Long productId);

    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKey, String descriptionKey, Pageable pageable);

    Page<Product> findAllByCategory_Name(String category, Pageable pageable);

    Page<Product> findByCategory_NameAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String category, String searchKey, String descriptionKey, Pageable pageable);
}