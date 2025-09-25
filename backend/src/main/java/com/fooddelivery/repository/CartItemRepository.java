package com.fooddelivery.repository;

import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.Food;
import com.fooddelivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByUser(User user);
    
    Optional<CartItem> findByUserAndFood(User user, Food food);
    
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user = :user")
    void deleteByUser(@Param("user") User user);
    
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user = :user AND c.food = :food")
    void deleteByUserAndFood(@Param("user") User user, @Param("food") Food food);
}


