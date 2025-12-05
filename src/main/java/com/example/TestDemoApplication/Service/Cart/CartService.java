package com.example.TestDemoApplication.Service.Cart;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.Cart.CartDto;
import com.example.TestDemoApplication.DTO.User.UserRegisterDTO;
import com.example.TestDemoApplication.Entity.Cart;
import com.example.TestDemoApplication.Entity.UserAuth;
import com.example.TestDemoApplication.Repository.CartRepository;
import com.example.TestDemoApplication.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
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

    private String generateCartId() {
        int length = 311;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
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
                cartId=generateCartId();
                cart.setCartId(cartId);
                cart.setProductId(productId);
                cart.setUserId(userId);
                cart.setQuantity(quantity);
                cart.setPrice(price);
                cartRepository.save(cart);
            }else if(cartId == null && userId != null){
                Optional<UserAuth> userOptional = userRepository.findById(userId);
                if(userOptional != null){
                    UserAuth user = userOptional.get();
                    cartId= user.getCartId();
                    cart.setProductId(productId);
                    cart.setUserId(userId);
                    cart.setQuantity(quantity);
                    cart.setPrice(price);
                    cartRepository.save(cart);
                }
            } else if (cartId != null) {
                cart.setCartId(cartId);
                cart.setProductId(productId);
                cart.setUserId(userId);
                cart.setQuantity(quantity);
                cart.setPrice(price);
                cartRepository.save(cart);
            }

        }catch(Exception e){

        }
        return "";

    }
}
