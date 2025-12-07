package com.example.TestDemoApplication.Repository;
import com.example.TestDemoApplication.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, String> {
    List<ImageEntity> findByProductIdOrderBySortOrderAsc(String productId);
    List<ImageEntity> findByProductId(String productId);
}