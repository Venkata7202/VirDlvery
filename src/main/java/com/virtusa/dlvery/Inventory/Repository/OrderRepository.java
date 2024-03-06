package com.virtusa.dlvery.Inventory.Repository;

import com.virtusa.dlvery.Inventory.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {


    Order findByOrderId(UUID orderId);


    List<Order> findByUser_UserId(UUID user);

    List<Order> findOrdersByDeliveryAddress(String deliveryAddress);

    List<Order> findOrdersByOrderDate(LocalDate orderDate);

    List<Order> findByIsCompleted(boolean isCompleted);

}
