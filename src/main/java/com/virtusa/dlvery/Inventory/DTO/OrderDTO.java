package com.virtusa.dlvery.Inventory.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virtusa.dlvery.Inventory.Entities.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private UUID orderId;
    private UUID userId; // Assuming this is the user ID associated with the order
    private String deliveryAddress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    private boolean isCompleted;

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId(); // Assuming there is a getUserId() method in User entity
        this.deliveryAddress = order.getDeliveryAddress();
        this.orderDate = order.getOrderDate();
        this.isCompleted = order.isCompleted();
    }
}


