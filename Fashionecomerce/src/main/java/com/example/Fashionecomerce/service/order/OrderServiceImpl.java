package com.example.Fashionecomerce.service.order;

import com.example.Fashionecomerce.dtos.OrderDto;
import com.example.Fashionecomerce.enums.OrderStatus;
import com.example.Fashionecomerce.model.Cart;
import com.example.Fashionecomerce.model.Order;
import com.example.Fashionecomerce.model.OrderItem;
import com.example.Fashionecomerce.model.Product;
import com.example.Fashionecomerce.repository.OrderRepository;
import com.example.Fashionecomerce.repository.ProductRepository;
import com.example.Fashionecomerce.service.cart.CartServiceImpl;
import com.example.Fashionecomerce.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final ICartService cartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId) ;
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return saveOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getUnitPrice(),
                    cartItem.getQuantity());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList) {
        return orderItemsList.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
