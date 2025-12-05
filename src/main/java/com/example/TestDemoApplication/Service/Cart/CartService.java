package com.example.TestDemoApplication.Service.Cart;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.Cart.CartDto;
import com.example.TestDemoApplication.Entity.Cart;
import com.example.TestDemoApplication.Entity.UserAuth;
import com.example.TestDemoApplication.Repository.CartRepository;
import com.example.TestDemoApplication.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @org.jetbrains.annotations.NotNull
    private String generateCartId() {
        int length = 31;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String validateCartId(String cartId) {
        Optional<Cart> cartEntity = cartRepository.findById(cartId);
        Optional<UserAuth> userAuthEntity= userRepository.findById(cartId);
        while(cartEntity.isPresent() || userAuthEntity.isPresent()) {
            cartId = generateCartId();
            cartEntity = cartRepository.findById(cartId);
            userAuthEntity=userRepository.findById(cartId);
        }
        return cartId;
    }

    public String addInCart(CartDto dto, HttpServletRequest request, HttpServletResponse response){
        try{
            Cart cart= new Cart();
            String cartId= AESUtil.decrypt(dto.getCartId());
            String userId= AESUtil.decrypt(dto.getUserId());
            String productId= AESUtil.decrypt(dto.getProductId());
            Long quantity= Long.parseLong(String.valueOf(dto.getQuantity()));
            Long price= Long.parseLong(String.valueOf(dto.getPrice()));
            if(request.getCookies()!=null){
                for(Cookie cookie: request.getCookies()){
                    if("Cart Id".equals(cookie.getName())){
                        cartId=cookie.getName();
                        break;
                    }
                }
            }
            if(cartId == null && userId== null){
                // Item  add for first time guest.
                cartId=validateCartId(generateCartId());

                cart.setCartId(cartId);
                cart.setProductId(productId);
                cart.setUserId(userId);
                cart.setQuantity(quantity);
                cart.setPrice(price);
                cartRepository.save(cart);
            }else if(cartId == null && userId != null){
                Optional<UserAuth> userOptional = userRepository.findById(userId);
                if(userOptional != null){
                    // Item add for regular User
                    UserAuth user = userOptional.get();
                    cartId= user.getCartId();
                    cart.setProductId(productId);
                    cart.setUserId(userId);
                    cart.setQuantity(quantity);
                    cart.setPrice(price);
                    cartRepository.save(cart);
                }
            } else if (cartId != null) {
                Optional<Cart> existingCartItem = cartRepository.findByCartIdAndProductId(cartId, productId);
                if (existingCartItem.isPresent()) {
                    // Product already exists in cart → update quantity
                    Cart cartq = existingCartItem.get();
                    Long currentQty = cartq.getQuantity();
                    cartq.setQuantity(currentQty + quantity);   // add quantity to existing one
                    cartRepository.save(cart);
                }else{
                    // item add for Guest
                    cart.setCartId(cartId);
                    cart.setProductId(productId);
                    cart.setUserId(userId);
                    cart.setQuantity(quantity);
                    cart.setPrice(price);
                    cartRepository.save(cart);
                }

            }
        }catch(Exception e){

        }
        return "Item added in your cart";

    }

    public String deleteItemFromCart(String cartId, String productId) {
        try{
            cartId= AESUtil.decrypt(cartId);
            productId= AESUtil.decrypt(productId);
            Optional<Cart> cart= cartRepository.findByCartIdAndProductId(cartId,productId);
            if(cart.isPresent()){
                cartRepository.deleteByCartIdAndProductId(cartId, productId);
                return "Product deleted from cart ";
            }else{
                return "Product or Cart is not available";
            }
        }catch(Exception e){
            return "Invalid product id or cartid"+e.getMessage();
        }
    }
    public boolean decereseQuantity(String cartId, String productId){
        try{
            cartId= AESUtil.decrypt(cartId);
            productId= AESUtil.decrypt(productId);
            Optional<Cart> cart= cartRepository.findByCartIdAndProductId(cartId,productId);

        }catch(Exception e){
            return true;
        }
        return true;
    }
}
