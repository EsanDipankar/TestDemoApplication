package com.example.TestDemoApplication.Service.Order;

import com.example.TestDemoApplication.Service.Inventory.InventoryService;
import com.example.TestDemoApplication.Service.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InventoryService inventoryService;

    public String orderProduct(String productId, String userId, double amount) {

        // 1. Validate Inputs
        if (productId == null || productId.isEmpty()) {
            return "Product ID cannot be empty.";
        }
        if (userId == null || userId.isEmpty()) {
            return "User ID cannot be empty.";
        }

        // 2. Check Stock
        Long stock = inventoryService.checkProductStock(productId);
        if (stock == null || stock <= 0) {
            return "Product is out of stock.";
        }

        // 3. Create Order ID (UUID or DB-generated)
        String orderId = UUID.randomUUID().toString();

        // 4. Initiate Payment
        String paymentStatus = paymentService.initiatePayment(orderId, userId, amount);

        // 5. Handle Payment Response
        if ("SUCCESS".equals(paymentStatus)) {

            // Reduce stock by 1
            inventoryService.reduceStock(productId, 1);

            // Save or update order status
            updateOrderStatus(orderId, "CONFIRMED");

            return "Order Confirmed! Order ID: " + orderId;

        } else {

            updateOrderStatus(orderId, "PAYMENT_FAILED");

            return "Payment Failed! Order ID: " + orderId;
        }
    }

    // Dummy Method (You will implement in DB)
    private void updateOrderStatus(String orderId, String status) {
        System.out.println("Order " + orderId + " updated to: " + status);
    }
}
