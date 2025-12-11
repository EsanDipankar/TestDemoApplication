package com.example.TestDemoApplication.Service.Order;

import com.example.TestDemoApplication.Entity.Order;
import com.example.TestDemoApplication.Entity.OrderStatus;
import com.example.TestDemoApplication.Entity.PaymentInitiationResult;
import com.example.TestDemoApplication.Repository.OrderRepository;
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

    @Autowired
    private OrderRepository orderRepository;

    public PaymentInitiationResult  orderProduct(String productId, String userId, Long amount) {

        // 1. Validate Inputs
        if (productId == null || productId.isEmpty()) throw new RuntimeException("Invalid Product ID");
        if (userId == null || userId.isEmpty()) throw new RuntimeException("Invalid User ID");

        // 2. Check Stock
        Long stock = inventoryService.checkProductStock(productId);
        if (stock == null || stock <= 0) {
            throw new RuntimeException("Product is out of stock");
        }
        // 3. Create internal Order
        String internalOrderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderId(internalOrderId);
        order.setProductId(productId);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setStatus(OrderStatus.CREATED);

        orderRepository.save(order);



        // 5. Update Razorpay order ID
        PaymentInitiationResult paymentResult = paymentService.initiatePayment(internalOrderId, userId, amount);

        order.setRazorpayOrderId(paymentResult.getRazorpayOrderId());
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        orderRepository.save(order);

        return paymentResult;
    }

    public void updateOrderStatusAfterPayment(String orderId, String status,
                                              String paymentId, String signature) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (status.equals("SUCCESS")) {
            order.setStatus(OrderStatus.CONFIRMED);
            order.setRazorpayPaymentId(paymentId);
            order.setRazorpaySignature(signature);

            inventoryService.reduceStock(order.getProductId(), 1);
        } else {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        orderRepository.save(order);
    }
    public boolean verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        return paymentService.verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);
    }
    public void updateOrderOnPaymentSuccess(String razorpayOrderId, String paymentId) {

        Order order = orderRepository.findAll()
                .stream()
                .filter(o -> razorpayOrderId.equals(o.getRazorpayOrderId()))
                .findFirst()
                .orElse(null);

        if (order == null) {
            throw new RuntimeException("Order Not Found using razorpayOrderId");
        }

        // Update order status
        order.setRazorpayPaymentId(paymentId);
        order.setStatus(OrderStatus.CONFIRMED);

        // Reduce product stock
        inventoryService.reduceStock(order.getProductId(), 1);

        orderRepository.save(order);
    }

}
