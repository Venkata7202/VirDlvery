package com.virtusa.dlvery.Delivery.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "delivery_agents")
public class DeliveryAgent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "agent_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID agentId;

    @Column(name = "agent_name")
    private String agentName;

    @PrePersist
    public void prePersist() {
        if (this.agentId == null) {
            this.agentId = UUID.randomUUID();
        }
    }
}
