package com.example.TestDemoApplication.Controller.Order;

import com.example.TestDemoApplication.Service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    public OrderService orderService;

    @RequestMapping("/orderSingleProduct")
    public String orderSingleProduct(@RequestParam String productId, @RequestParam String userId){
        return orderService.orderProduct(productId,userId);
    }
}
