package com.virtusa.dlvery.common.Entities;

import com.virtusa.dlvery.Inventory.Entities.Warehouse;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "product_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "is_perishable")
    private boolean isPerishable;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "is_damaged")
    private boolean isDamaged;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @PrePersist
    public void prePersist() {
        if (this.productId == null) {
            this.productId = UUID.randomUUID();
        }
    }



}