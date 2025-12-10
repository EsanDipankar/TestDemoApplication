package com.example.TestDemoApplication.Controller.Order;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.Entity.PaymentInitiationResult;
import com.example.TestDemoApplication.Entity.Product;
import com.example.TestDemoApplication.Repository.ProductRepository;
import com.example.TestDemoApplication.Service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/orderSingleProduct")
    public PaymentInitiationResult orderSingleProduct(
            @RequestParam String productId,
            @RequestParam String userId) {

        try {
            // 1. Decrypt incoming IDs
            String prodId = AESUtil.decrypt(productId);
            String usrId  = AESUtil.decrypt(userId);

            // 2. Fetch product details
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // 3. Extract product amount
            Long amount = product.getPrice(); // amount must be in paise

            // 4. Call OrderService with decrypted IDs
            return orderService.orderProduct(prodId, usrId, amount);

        } catch (Exception e) {
            e.printStackTrace();
            return new PaymentInitiationResult(false, null, "Error: " + e.getMessage());
        }
    }
}