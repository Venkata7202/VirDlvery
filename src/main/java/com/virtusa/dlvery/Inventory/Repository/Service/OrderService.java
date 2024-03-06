package com.virtusa.dlvery.Inventory.Repository.Service;

import com.virtusa.dlvery.Inventory.DTO.OrderDTO;
import com.virtusa.dlvery.Inventory.Entities.Order;
import com.virtusa.dlvery.Inventory.Repository.OrderRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO findOrderById(UUID orderId) {
        logger.info("Fetching order by ID: {}", orderId);

        if (orderId == null) {
            logger.error("Order ID is null. Unable to fetch order.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Order order = orderRepository.findByOrderId(orderId);

        if (order == null) {
            logger.info("No order found with ID: {}", orderId);
            // Handle case when no order is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToOrderDTO(order);
    }

    public List<OrderDTO> findAllOrders() {
        logger.info("Fetching all orders");

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            logger.info("No orders found");
            // Handle case when no orders are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToOrderDTOList(orders);
    }

    public List<OrderDTO> findOrdersByUser(UUID userId) {
        logger.info("Fetching orders by user ID: {}", userId);

        List<Order> orders = orderRepository.findByUser_UserId(userId);

        if (orders.isEmpty()) {
            logger.info("No orders found for user ID: {}", userId);
            // Handle case when no orders are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToOrderDTOList(orders);
    }

    public List<OrderDTO> findOrdersByDeliveryAddress(String deliveryAddress) {
        logger.info("Fetching orders by delivery address: {}", deliveryAddress);

        List<Order> orders = orderRepository.findOrdersByDeliveryAddress(deliveryAddress);

        if (orders.isEmpty()) {
            logger.info("No orders found for delivery address: {}", deliveryAddress);
            // Handle case when no orders are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToOrderDTOList(orders);
    }

    public List<OrderDTO> findOrdersByOrderDate(LocalDate orderDate) {
        logger.info("Fetching orders by order date: {}", orderDate);

        List<Order> orders = orderRepository.findOrdersByOrderDate(orderDate);

        if (orders.isEmpty()) {
            logger.info("No orders found for order date: {}", orderDate);
            // Handle case when no orders are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToOrderDTOList(orders);
    }

    public List<OrderDTO> findOrdersByCompletionStatus(boolean isCompleted) {
        logger.info("Fetching orders by completion status: {}", isCompleted);

        List<Order> orders = orderRepository.findByIsCompleted(isCompleted);

        if (orders.isEmpty()) {
            logger.info("No orders found with completion status: {}", isCompleted);
            // Handle case when no orders are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToOrderDTOList(orders);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        logger.info("Creating order for user ID: {}", orderDTO.getUserId());

        if (orderDTO.getUserId() == null) {
            logger.error("User ID is null. Unable to create order.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Order order = DTOConversionUtil.convertToOrder(orderDTO);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToOrderDTO(savedOrder);
    }

    public OrderDTO updateOrder(UUID orderId, OrderDTO updatedOrderDTO) {
        logger.info("Updating order with ID: {}", orderId);

        // Check if order with given ID exists
        Order existingOrder = orderRepository.findByOrderId(orderId);
        if (existingOrder == null) {
            logger.info("No order found with ID: {}", orderId);
            // Handle case when no order is found, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Order updatedOrder = DTOConversionUtil.convertToOrder(updatedOrderDTO);

        // Set the ID for the existing order
        updatedOrder.setOrderId(orderId);

        // Save updated order
        Order savedOrder = orderRepository.save(updatedOrder);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToOrderDTO(savedOrder);
    }

    public void deleteOrder(UUID orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        orderRepository.deleteById(orderId);
    }
}
