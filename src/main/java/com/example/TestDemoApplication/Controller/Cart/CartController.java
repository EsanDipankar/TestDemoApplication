package com.example.TestDemoApplication.Controller.Cart;

import com.example.TestDemoApplication.DTO.Cart.CartDto;
import com.example.TestDemoApplication.Entity.Cart;
import com.example.TestDemoApplication.Service.Cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart")
    public String addToCart(@RequestBody CartDto cartDto, HttpServletRequest request, HttpServletResponse response){
        String add= cartService.addInCart(cartDto, request,response);
        return add;
    }
    @DeleteMapping("/deleteFormCart")
    public String deleteFromCart(@RequestParam String cartId, @RequestParam String productId){
        return cartService.deleteItemFromCart(cartId, productId);
    }
    @GetMapping("/showCart")
    public List<Cart> ShowCart(@RequestParam String cartId){
        List<Cart> cartList= new ArrayList<>();
        cartList=cartService.getCarts(cartId);
        return cartList;
    }


}
