package com.virtusa.dlvery.Delivery.Entities;

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
@Table(name = "delivery_tracking")
public class DeliveryTracking {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tracking_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID trackingId;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_signature")
    private byte[] customerSignature;

    @Column(name = "door_lock_status")
    private boolean doorLockStatus;

    @Column(name = "is_missed_delivery")
    private boolean isMissedDelivery;

    @Column(name = "is_damaged_delivery")
    private boolean isDamagedDelivery;

    @Column(name = "is_return_delivery")
    private boolean isReturnDelivery;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}



