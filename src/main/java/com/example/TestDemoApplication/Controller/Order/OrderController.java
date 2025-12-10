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
    public OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/orderSingleProduct") // Use POST for order creation
    public PaymentInitiationResult orderSingleProduct(
            @RequestParam String productId,
            @RequestParam String userId) {
        try{
            String prodId= AESUtil.decrypt(productId);
            String usrId=AESUtil.decrypt(userId);
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Long amount = product.getPrice(); // Get product price
            return orderService.orderProduct(productId, userId, amount);
        }catch (Exception e){
            e.printStackTrace();
            // Return failed PaymentInitiationResult
            return new PaymentInitiationResult(false, null, "Error: " + e.getMessage());
        }


    }
}
