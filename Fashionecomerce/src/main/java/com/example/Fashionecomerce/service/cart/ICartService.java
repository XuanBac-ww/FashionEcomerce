package com.example.Fashionecomerce.service.cart;

import com.example.Fashionecomerce.dtos.CartDto;
import com.example.Fashionecomerce.model.Cart;
import com.example.Fashionecomerce.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long cartId);

    Cart getCartByUserId(Long userId);

    void clearCart(Long cartId);

    Cart initializeNewCartForUser(User user);

    BigDecimal getTotalPrice(Long cartId);

    CartDto convertToDto(Cart cart);
}
