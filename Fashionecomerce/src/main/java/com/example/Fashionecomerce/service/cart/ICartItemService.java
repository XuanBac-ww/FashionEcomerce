package com.example.Fashionecomerce.service.cart;


import com.example.Fashionecomerce.dtos.CartItemDto;
import com.example.Fashionecomerce.model.CartItem;

public interface ICartItemService {
    CartItem addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);
    CartItem getCartItem(Long cartId,Long productId);

    CartItemDto converToDto(CartItem cartItem);
}
