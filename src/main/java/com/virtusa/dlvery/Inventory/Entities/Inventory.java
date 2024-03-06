package com.virtusa.dlvery.Inventory.Entities;

import com.virtusa.dlvery.Inventory.Enum.MovementType;
import com.virtusa.dlvery.common.Entities.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "movement_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID movementId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity_in")
    private int quantityIn;

    @Column(name = "quantity_out")
    private int quantityOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type")
    private MovementType movementType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @PrePersist
    public void prePersist() {
        if (this.movementId == null) {
            this.movementId = UUID.randomUUID();
        }
    }

}



