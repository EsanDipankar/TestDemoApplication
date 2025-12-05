package com.example.TestDemoApplication.Controller.Cart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @RequestMapping("/addtoCart")
    public String addToCart(@RequestParam String cartId){
        String add= CartService.addToCart(cartId);
    }


}
