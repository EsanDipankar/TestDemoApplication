package com.example.TestDemoApplication.Service.Inventory;

import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Entity.Product;
import com.example.TestDemoApplication.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public Long checkProductStock(String productId){
        Product product= inventoryRepository.findByProductId(productId);
        return product.getQuantity();
    }
    public void reduceStock(String productId, int quantity) {
        Product product = inventoryRepository.findByProductId(productId);
        if (product != null) {
            product.setQuantity(product.getQuantity() - quantity);
            inventoryRepository.save(product);
        }
    }


}
