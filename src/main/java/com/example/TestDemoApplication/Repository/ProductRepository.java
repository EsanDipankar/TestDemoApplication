package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryContainingIgnoreCase(String keyword);
    List<Product> findBySubCategoryContainingIgnoreCase(String keyword);
    List<Product> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSubCategoryContainingIgnoreCase(
            String productName, String category, String subCategory
    );

}
