package com.virtusa.dlvery.Inventory.Controller;

import com.virtusa.dlvery.Inventory.DTO.OrderDTO;
import com.virtusa.dlvery.Inventory.Repository.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID orderId) {
        logger.info("Received request to get order by ID: {}", orderId);

        OrderDTO order = orderService.findOrderById(orderId);

        if (order == null) {
            logger.info("No order found with ID: {}", orderId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning order: {}", order.getOrderId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        logger.info("Received request to get all orders");

        List<OrderDTO> orders = orderService.findAllOrders();

        if (orders.isEmpty()) {
            logger.info("No orders found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable UUID userId) {
        logger.info("Received request to get orders by user ID: {}", userId);

        List<OrderDTO> orders = orderService.findOrdersByUser(userId);

        if (orders.isEmpty()) {
            logger.info("No orders found for user ID: {}", userId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byDeliveryAddress/{deliveryAddress}")
    public ResponseEntity<List<OrderDTO>> getOrdersByDeliveryAddress(@PathVariable String deliveryAddress) {
        logger.info("Received request to get orders by delivery address: {}", deliveryAddress);

        List<OrderDTO> orders = orderService.findOrdersByDeliveryAddress(deliveryAddress);

        if (orders.isEmpty()) {
            logger.info("No orders found for delivery address: {}", deliveryAddress);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byOrderDate/{orderDate}")
    public ResponseEntity<List<OrderDTO>> getOrdersByOrderDate(@PathVariable LocalDate orderDate) {
        logger.info("Received request to get orders by order date: {}", orderDate);

        List<OrderDTO> orders = orderService.findOrdersByOrderDate(orderDate);

        if (orders.isEmpty()) {
            logger.info("No orders found for order date: {}", orderDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byCompletionStatus/{isCompleted}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCompletionStatus(@PathVariable boolean isCompleted) {
        logger.info("Received request to get orders by completion status: {}", isCompleted);

        List<OrderDTO> orders = orderService.findOrdersByCompletionStatus(isCompleted);

        if (orders.isEmpty()) {
            logger.info("No orders found with completion status: {}", isCompleted);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Validated @RequestBody OrderDTO orderDTO) {
        logger.info("Received request to create order for user ID: {}", orderDTO.getUserId());

        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        if (createdOrder == null) {
            logger.info("Order creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Order created successfully with ID: {}", createdOrder.getOrderId());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable UUID orderId,
            @Validated @RequestBody OrderDTO updatedOrderDTO) {
        logger.info("Received request to update order with ID: {}", orderId);

        OrderDTO updatedOrder = orderService.updateOrder(orderId, updatedOrderDTO);

        if (updatedOrder == null) {
            logger.info("Order update failed. No order found with ID: {}", orderId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Order updated successfully with ID: {}", updatedOrder.getOrderId());
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        logger.info("Received request to delete order with ID: {}", orderId);

        orderService.deleteOrder(orderId);

        logger.info("Order deleted successfully with ID: {}", orderId);
        return ResponseEntity.noContent().build();
    }
}
