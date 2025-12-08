package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryContainingIgnoreCase(String keyword);
    List<Product> findBySubCategoryContainingIgnoreCase(String keyword);
    List<Product> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSubCategoryContainingIgnoreCase(
            String productName, String category, String subCategory
    );

}
