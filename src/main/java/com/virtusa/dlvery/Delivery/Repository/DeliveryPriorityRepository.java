package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryPriorityRepository extends JpaRepository<DeliveryPriority, UUID> {

    DeliveryPriority findByPriorityName(String priorityName);
}

