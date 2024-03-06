package com.virtusa.dlvery.Inventory.Entities;

import com.virtusa.dlvery.common.Entities.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "warehouse_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private int capacity;


    @OneToMany(mappedBy = "warehouse")
    private Set<Inventory> inventory = new HashSet<>();


    @PrePersist
    public void prePersist() {
        if (this.warehouseId == null) {
            this.warehouseId = UUID.randomUUID();
        }
    }
}