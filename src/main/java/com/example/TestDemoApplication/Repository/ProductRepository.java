package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findById(Long productId);
    void deleteById(Long productId);

}
