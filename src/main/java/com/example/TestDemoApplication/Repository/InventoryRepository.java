package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Product, String> {
    Product findByProductId(String productId);
}
