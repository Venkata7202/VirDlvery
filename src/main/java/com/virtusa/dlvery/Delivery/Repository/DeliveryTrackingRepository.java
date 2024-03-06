package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, UUID> {

    List<DeliveryTracking> findByDelivery_DeliveryId(UUID deliveryId);

    List<DeliveryTracking> findByCustomerName(String customerName);

    List<DeliveryTracking> findByDoorLockStatus(boolean doorLockStatus);

    List<DeliveryTracking> findByIsMissedDelivery(boolean isMissedDelivery);

    List<DeliveryTracking> findByIsDamagedDelivery(boolean isDamagedDelivery);

    List<DeliveryTracking> findByIsReturnDelivery(boolean isReturnDelivery);

}

