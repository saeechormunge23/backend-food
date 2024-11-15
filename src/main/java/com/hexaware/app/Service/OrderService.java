package com.hexaware.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.app.Dao.FoodItemRepository;
import com.hexaware.app.Dao.OrderRepository;
import com.hexaware.app.Dao.UserRepository;
import com.hexaware.app.Dto.FoodItem;
import com.hexaware.app.Dto.Orders;
import com.hexaware.app.Dto.User;
import com.hexaware.app.Enums.OrderStatus;
import com.hexaware.app.Exception.IDnotfoundException;

import jakarta.validation.Valid;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    // Place an order
    public Orders placeOrder(Orders order) {
        // Fetch the user and food item from the repositories
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + order.getUser().getId()));
        
        FoodItem foodItem = foodItemRepository.findById(order.getFoodItem().getId())
                .orElseThrow(() -> new RuntimeException("Food item not found with ID: " + order.getFoodItem().getId()));

        // Check if the quantity requested is available
        if (foodItem.getQuantityAvailable() < order.getQuantity()) {
            throw new RuntimeException("Insufficient quantity available for the item: " + foodItem.getName());
        }

        // Update the food item's quantity
        foodItem.setQuantityAvailable(foodItem.getQuantityAvailable() - order.getQuantity());
        foodItemRepository.save(foodItem);

        // Set the user and food item for the order and save it
        order.setUser(user);
        order.setFoodItem(foodItem);

        return orderRepository.save(order);
    }
    
    // Get all orders
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public List<Orders> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Get order by ID
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    // Update order status
    public Orders updateOrderStatus(Long id, String status) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        try {
            order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }
        return orderRepository.save(order);
    }

    // Delete order by ID
    public void deleteOrder(Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        orderRepository.delete(order);
    }

}
