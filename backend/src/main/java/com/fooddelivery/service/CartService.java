package com.fooddelivery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.Food;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.CartItemRepository;
import com.fooddelivery.repository.FoodRepository;
import com.fooddelivery.repository.UserRepository;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    // Return cart items for a user as Map<String, Integer>
    public Map<String, Integer> getCartData(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        Map<String, Integer> cartData = new HashMap<>();

        for (CartItem item : cartItems) {
            // convert Long → String for frontend
            cartData.put(item.getFood().getId().toString(), item.getQuantity());
        }

        return cartData;
    }

    // Add a food item to cart
    public void addToCart(String userEmail, String foodId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long foodIdLong = Long.parseLong(foodId); // convert String → Long
        Food food = foodRepository.findById(foodIdLong)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Optional<CartItem> existingItem = cartItemRepository.findByUserAndFood(user, food);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem(user, food, 1);
            cartItemRepository.save(cartItem);
        }
    }

    // Remove a food item from cart
    public void removeFromCart(String userEmail, String foodId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long foodIdLong = Long.parseLong(foodId); // convert String → Long
        Food food = foodRepository.findById(foodIdLong)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Optional<CartItem> cartItem = cartItemRepository.findByUserAndFood(user, food);

        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemRepository.save(item);
            } else {
                cartItemRepository.delete(item);
            }
        }
    }

    // Clear all items from user's cart
    public void clearCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartItemRepository.deleteByUser(user);
    }
}
