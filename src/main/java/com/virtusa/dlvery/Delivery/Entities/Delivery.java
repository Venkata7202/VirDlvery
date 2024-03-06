package com.virtusa.dlvery.Delivery.Entities;

import com.virtusa.dlvery.Delivery.Enum.DeliveryStatus;
import com.virtusa.dlvery.Inventory.Entities.Warehouse;
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
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "delivery_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID deliveryId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id")
    private DeliveryAgent deliveryAgent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_damaged")
    private boolean isDamaged;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @PrePersist
    public void prePersist() {
        if (this.deliveryId == null) {
            this.deliveryId = UUID.randomUUID();
        }
    }
}

