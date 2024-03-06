package com.virtusa.dlvery.common.Entities;

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
@Table(name = "user_assigned_roles")
public class UserAssignedRole {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "assignment_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID assignmentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;


    @PrePersist
    public void prePersist() {
        if (this.assignmentId == null) {
            this.assignmentId = UUID.randomUUID();
        }
    }
}
