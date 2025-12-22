package com.example.TestDemoApplication.Service.Cart;

import com.example.TestDemoApplication.Entity.Cart;
import com.example.TestDemoApplication.Repository.CartRepository;
import com.example.TestDemoApplication.Repository.UserRepository;
import com.example.TestDemoApplication.Config.AESUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void deleteItemFromCart_shouldDelete_whenCartExists() {

        // 1️⃣ Arrange (Prepare data)
        String encryptedCartId = "encryptedCartId";
        String encryptedProductId = "encryptedProductId";

        String cartId = "cart123";
        String productId = "prod123";

        Cart cart = new Cart();

        // 2️⃣ Mock static AESUtil.decrypt()
        try (MockedStatic<AESUtil> aesMock = mockStatic(AESUtil.class)) {

            aesMock.when(() -> AESUtil.decrypt(encryptedCartId)).thenReturn(cartId);
            aesMock.when(() -> AESUtil.decrypt(encryptedProductId)).thenReturn(productId);

            when(cartRepository.findByCartIdAndProductId(cartId, productId))
                    .thenReturn(Optional.of(cart));

            // 3️⃣ Act (Call method)
            String result = cartService.deleteItemFromCart(encryptedCartId, encryptedProductId);

            // 4️⃣ Assert (Verify result)
            assertEquals("Product deleted from cart ", result);

            // 5️⃣ Verify repository call
            verify(cartRepository).deleteByCartIdAndProductId(cartId, productId);
        }
    }
}
