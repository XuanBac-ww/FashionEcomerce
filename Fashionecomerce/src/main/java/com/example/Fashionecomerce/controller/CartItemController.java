package com.example.Fashionecomerce.controller;

import com.example.Fashionecomerce.dtos.CartItemDto;
import com.example.Fashionecomerce.model.Cart;
import com.example.Fashionecomerce.model.CartItem;
import com.example.Fashionecomerce.model.User;
import com.example.Fashionecomerce.response.ApiResponse;
import com.example.Fashionecomerce.service.cart.ICartItemService;
import com.example.Fashionecomerce.service.cart.ICartService;
import com.example.Fashionecomerce.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final IUserService userService;
    private final ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
        User user = userService.getAuthenticatedUser();
        Cart cart = cartService.initializeNewCartForUser(user);
        CartItem cartItem = cartItemService.addItemToCart(cart.getId(), productId, quantity);
        CartItemDto cartItemDto = cartItemService.converToDto(cartItem);
        return ResponseEntity.ok(new ApiResponse("Item added successfully!", cartItemDto));
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Item removed successfully!", null));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId,
                                                      @PathVariable Long itemId,
                                                      @RequestParam int quantity) {

        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new ApiResponse("Item updated successfully!", null));
    }
}
