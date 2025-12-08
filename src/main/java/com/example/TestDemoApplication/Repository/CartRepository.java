package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByCartId(String cartId);
    Optional<Cart> findByCartIdAndProductId(String cartId, String productId);
    void deleteByCartIdAndProductId(String cartId, String productId);

}
