package com.virtusa.dlvery.Delivery.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "delivery_priorities")
public class DeliveryPriority {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "priority_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID priorityId;

    @Column(name = "priority_name")
    private String priorityName;

    @PrePersist
    public void prePersist() {
        if (this.priorityId == null) {
            this.priorityId = UUID.randomUUID();
        }
    }
}
