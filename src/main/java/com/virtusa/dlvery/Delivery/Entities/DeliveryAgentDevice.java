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
@Table(name = "delivery_agent_devices")
public class DeliveryAgentDevice {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "device_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID deviceId;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private DeliveryAgent agent;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    private String deviceType;

    @PrePersist
    public void prePersist() {
        if (this.deviceId == null) {
            this.deviceId = UUID.randomUUID();
        }
    }


}

