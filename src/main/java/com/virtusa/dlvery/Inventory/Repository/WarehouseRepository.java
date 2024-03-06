package com.virtusa.dlvery.Inventory.Repository;

import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    List<Warehouse> findByWarehouseName(String warehouseName);

    List<Warehouse> findByLocation(String location);

    List<Warehouse> findByCapacityGreaterThan(int capacity);

    List<Warehouse> findByCapacityLessThan(int capacity);


    Warehouse findByWarehouseId(UUID warehouseId);

}

