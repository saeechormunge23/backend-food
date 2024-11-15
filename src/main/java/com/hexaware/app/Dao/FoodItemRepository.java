package com.hexaware.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.app.Dto.FoodItem;


@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
	 @Query("SELECT f FROM FoodItem f WHERE LOWER(f.category) = LOWER(:searchTerm) OR LOWER(f.hotelName) = LOWER(:searchTerm)")
	    List<FoodItem> findByCategoryOrHotelName(@Param("searchTerm") String searchTerm);
}