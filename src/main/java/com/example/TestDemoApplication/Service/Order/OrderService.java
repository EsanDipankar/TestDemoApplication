package com.example.TestDemoApplication.Service.Order;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
    @Autowired
    private PaymentService paymentService;
    public String orderProduct(String productId, String userId) {
        if (productId == null || productId.isEmpty()) {
            return "Product ID cannot be empty.";
        }

        if (userId == null || userId.isEmpty()) {
            return "User ID cannot be empty.";
        }
        boolean inStock = checkProductStock(productId);
        if(!inStock) {
            return "Product is out of stock";
        }
        String paymentStatus = paymentService.initiatePayment(orderId, userId);

        // 5. Update final order status
        if(paymentStatus.equals("SUCCESS")) {
            updateOrderStatus(orderId, "CONFIRMED");
            return "Order Confirmed! Order ID: " + orderId;
        } else {
            updateOrderStatus(orderId, "PAYMENT_FAILED");
            return "Payment Failed! Order ID: " + orderId;
        }
    }
}
