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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "agent_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID categoryId;

    @Column(name = "category_name")
    private String categoryName;


    @PrePersist
    public void prePersist() {
        if (this.categoryId == null) {
            this.categoryId = UUID.randomUUID();
        }
    }
}
