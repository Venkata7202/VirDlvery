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
@Table(name = "delivery_schedule")
public class DeliverySchedule {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "schedule_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID scheduleId;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private DeliveryAgent agent;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "delivery_data")
    private String deliveryData;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private DeliveryPriority priority;

    @Column(name = "is_rescheduled")
    private boolean isRescheduled;

    @PrePersist
    public void prePersist() {
        if (this.scheduleId == null) {
            this.scheduleId = UUID.randomUUID();
        }
    }


}
