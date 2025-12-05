package com.example.TestDemoApplication.Controller.Cart;

import com.example.TestDemoApplication.DTO.Cart.CartDto;
import com.example.TestDemoApplication.Service.Cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/addtoCart")
    public String addToCart(@RequestParam CartDto cartDto, @RequestParam HttpServletRequest request, @RequestParam HttpServletResponse response){
        String add= cartService.addInCart(cartDto, request,response);
        return add;
    }


}
