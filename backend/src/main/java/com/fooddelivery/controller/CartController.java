package com.fooddelivery.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.service.CartService;
import com.fooddelivery.service.UserService;
import com.fooddelivery.util.JwtUtil;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = "http://ec2-98-84-110-225.compute-1.amazonaws.com:5173", allowCredentials = "true") // ✅ match frontend port
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Get cart for logged-in user
    @PostMapping("get")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> getCart(@RequestHeader("token") String token) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token"));
            }
            String userEmail = jwtUtil.extractUsername(token);
            Map<String, Integer> cartData = cartService.getCartData(userEmail);
            return ResponseEntity.ok(ApiResponse.success(cartData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // Add item to cart
    @PostMapping("add")
    public ResponseEntity<ApiResponse<String>> addToCart(@RequestHeader("token") String token,
                                                        @RequestBody Map<String, Object> request) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token"));
            }

            String userEmail = jwtUtil.extractUsername(token);
            Object itemIdObj = request.get("itemId");

            if (itemIdObj == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Item ID is required"));
            }

            String itemId = itemIdObj.toString(); // ✅ use string _id
            cartService.addToCart(userEmail, itemId);

            return ResponseEntity.ok(ApiResponse.success("Item added to cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // Remove item from cart
    @PostMapping("remove")
    public ResponseEntity<ApiResponse<String>> removeFromCart(@RequestHeader("token") String token,
                                                             @RequestBody Map<String, Object> request) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token"));
            }

            String userEmail = jwtUtil.extractUsername(token);
            Object itemIdObj = request.get("itemId");

            if (itemIdObj == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Item ID is required"));
            }

            String itemId = itemIdObj.toString(); // ✅ use string _id
            cartService.removeFromCart(userEmail, itemId);

            return ResponseEntity.ok(ApiResponse.success("Item removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}






