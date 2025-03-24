package com.example.Fashionecomerce.service.order;

import com.example.Fashionecomerce.dtos.OrderDto;
import com.example.Fashionecomerce.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    List<OrderDto> getUserOrders(Long userId);


    OrderDto convertToDto(Order order);
}
