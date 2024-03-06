// DeliveryHistory.java
package com.virtusa.dlvery.Delivery.Entities;

import com.virtusa.dlvery.Delivery.Enum.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "delivery_history")
public class DeliveryHistory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "history_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID historyId;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private DeliverySchedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;  // Describes the type of event (e.g., RESCHEDULED, DELIVERED, MISSED_DELIVERY)

    @Column(name = "event_description", length = 1000)
    private String eventDescription;  // Detailed description of the event

    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;  // Timestamp when the event occurred

    @Column(name = "delivery_agent_name")
    private String deliveryAgentName;  // Name of the delivery agent involved in the event

    @Column(name = "customer_name")
    private String customerName;  // Name of the customer associated with the delivery


    @PrePersist
    public void prePersist() {
        if (this.historyId == null) {
            this.historyId = UUID.randomUUID();
        }
    }

}
