package com.example.Fashionecomerce.repository;

import com.example.Fashionecomerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long cartId);

    List<CartItem> findByProductId(Long productId);
}
