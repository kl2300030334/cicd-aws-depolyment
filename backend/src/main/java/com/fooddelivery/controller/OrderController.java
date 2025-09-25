package com.fooddelivery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.OrderRequest;
import com.fooddelivery.entity.Order;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = {"http://ec2-98-84-110-225.compute-1.amazonaws.com:3000", "http://ec2-98-84-110-225.compute-1.amazonaws.com:5173"}, allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("place")
    public ResponseEntity<ApiResponse<Map<String, String>>> placeOrder(@RequestHeader("token") String token,
                                                                       @Valid @RequestBody OrderRequest orderRequest) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token"));
            }
            String userEmail = jwtUtil.extractUsername(token);
            Order order = orderService.placeOrder(userEmail, orderRequest);
            Map<String, String> data = new HashMap<>();
            // Redirect to frontend verify route simulating payment gateway return
            data.put("sessionUrl", "http://ec2-98-84-110-225.compute-1.amazonaws.com:5173/verify?success=true&orderId=" + order.getId());
            return ResponseEntity.ok(ApiResponse.success("Order placed successfully", data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("my-orders")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders(@RequestHeader("token") String token) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token"));
            }
            String userEmail = jwtUtil.extractUsername(token);
            List<Order> orders = orderService.getUserOrders(userEmail);
            return ResponseEntity.ok(ApiResponse.success(orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("verify")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifyPayment(@RequestBody Map<String, String> body) {
        try {
            boolean success = Boolean.parseBoolean(body.getOrDefault("success", "false"));
            Long orderId = Long.parseLong(body.get("orderId"));
            Order order = orderService.verifyAndUpdatePayment(orderId, success);
            Map<String, Object> data = new HashMap<>();
            data.put("orderId", order.getId());
            data.put("paymentStatus", order.getPaymentStatus());
            data.put("status", order.getStatus());
            return ResponseEntity.ok(ApiResponse.success("Payment verification processed", data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    }


