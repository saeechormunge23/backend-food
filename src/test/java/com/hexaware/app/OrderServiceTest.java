package com.hexaware.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.app.Dao.FoodItemRepository;
import com.hexaware.app.Dao.OrderRepository;
import com.hexaware.app.Dao.UserRepository;
import com.hexaware.app.Dto.FoodItem;
import com.hexaware.app.Dto.Orders;
import com.hexaware.app.Dto.User;
import com.hexaware.app.Enums.OrderStatus;
import com.hexaware.app.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FoodItemRepository foodItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllOrders_Success() {
        Orders order1 = new Orders();
        Orders order2 = new Orders();

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Orders> ordersList = orderService.getAllOrders();

        assertEquals(2, ordersList.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById_Success() {
        Orders order = new Orders();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders fetchedOrder = orderService.getOrderById(1L);

        assertNotNull(fetchedOrder);
        assertEquals(1L, fetchedOrder.getId());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Order not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        Orders order = new Orders();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        Orders updatedOrder = orderService.updateOrderStatus(1L, "COMPLETED");

        assertEquals(OrderStatus.COMPLETED, updatedOrder.getOrderStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrderStatus_InvalidStatus() {
        Orders order = new Orders();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrderStatus(1L, "INVALID_STATUS");
        });

        assertEquals("Invalid order status: INVALID_STATUS", exception.getMessage());
    }

    @Test
    void testDeleteOrder_Success() {
        Orders order = new Orders();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Order not found with ID: 1", exception.getMessage());
    }
}