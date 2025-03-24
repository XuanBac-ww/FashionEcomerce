package com.example.Fashionecomerce.controller;

import com.example.Fashionecomerce.dtos.OrderDto;
import com.example.Fashionecomerce.model.Order;
import com.example.Fashionecomerce.response.ApiResponse;
import com.example.Fashionecomerce.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/user/{userId}/place-order")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId){
        Order order = orderService.placeOrder(userId);
        OrderDto orderDto =  orderService.convertToDto(order);
        return ResponseEntity.ok(new ApiResponse("Order placed successfully!", orderDto));
    }

    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        List<OrderDto> orders = orderService.getUserOrders(userId);
        System.out.println("The orders : ============================" + Arrays.toString(orders.toArray()));
        return ResponseEntity.ok(new ApiResponse("Success!", orders));
    }

//    @PostMapping("/create-payment-intent")
//    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest request) throws StripeException {
//        String clientSecret = orderService.createPaymentIntent(request);
//        return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
//    }
}
