package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.Delivery;
import com.virtusa.dlvery.Delivery.Enum.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    List<Delivery> findByWarehouse_WarehouseId(UUID warehouseId);

    List<Delivery> findByProduct_ProductId(UUID productId);

    List<Delivery> findByDeliveryAgent_AgentId(UUID agentId);

    List<Delivery> findByDate(LocalDate date);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByIsDamaged(boolean isDamaged);

    void deleteByDeliveryId(UUID deliveryId);

    // Additional custom methods
    List<Delivery> findByWarehouse_WarehouseIdAndDateBetween(UUID warehouseId, LocalDate startDate, LocalDate endDate);

    List<Delivery> findByStatusAndDateBefore(DeliveryStatus status, LocalDate date);

    List<Delivery> findByProduct_ProductIdAndIsDamaged(UUID productId, boolean isDamaged);


    Delivery findByDeliveryId(UUID deliveryId);
}
