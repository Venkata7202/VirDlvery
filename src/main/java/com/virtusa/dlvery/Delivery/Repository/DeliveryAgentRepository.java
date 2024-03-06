package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, UUID> {

    DeliveryAgent findByAgentName(String agentName);

    void deleteByAgentId(UUID agentId);

    List<DeliveryAgent> findByAgentNameContaining(String partialName);

    DeliveryAgent findDeliveryAgentByAgentId(UUID agentId);

}
