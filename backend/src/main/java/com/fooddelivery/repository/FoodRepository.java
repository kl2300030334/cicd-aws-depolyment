package com.fooddelivery.repository;

import com.fooddelivery.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // Fetch all foods marked as available
    List<Food> findByIsAvailableTrue();

    // Fetch all available foods belonging to a specific category
    List<Food> findByCategoryAndIsAvailableTrue(String category);

    // Fetch all available foods whose name contains the given substring
    @Query("SELECT f FROM Food f WHERE f.name LIKE %:name% AND f.isAvailable = true")
    List<Food> findByNameContainingAndIsAvailableTrue(@Param("name") String name);

    // Fetch the distinct list of available food categories
    @Query("SELECT DISTINCT f.category FROM Food f WHERE f.isAvailable = true")
    List<String> findDistinctCategories();
}







