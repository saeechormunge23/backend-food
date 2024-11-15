package com.hexaware.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.app.Dto.FoodItem;
import com.hexaware.app.Dto.Orders;
import com.hexaware.app.Dto.User;



@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);
    List<Orders> findByUserId(Long userId);
}
