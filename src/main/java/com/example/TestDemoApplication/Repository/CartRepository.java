package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByCartIdAndProductId(String cartId, String productId);
    void deleByCartIdAndProductId(String cartId, String productId);
}
