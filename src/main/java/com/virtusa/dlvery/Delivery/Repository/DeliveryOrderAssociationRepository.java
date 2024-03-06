package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryOrderAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryOrderAssociationRepository extends JpaRepository<DeliveryOrderAssociation, UUID> {

    List<DeliveryOrderAssociation> findByDelivery_DeliveryId(UUID deliveryId);

    List<DeliveryOrderAssociation> findByOrder_OrderId(UUID orderId);

    void deleteByDelivery_DeliveryIdAndOrder_OrderId(UUID deliveryId, UUID orderId);
}
