package com.investment.goldsilver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing current market price for metals
 */
@Entity
@Table(name = "metal_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Metal type is required")
    @Column(nullable = false, unique = true)
    private MetalType metalType;

    @NotNull(message = "Price per gram is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerGram;

    @NotNull
    @Column(nullable = false)
    private LocalDate updatedOn;

    @PrePersist
    @PreUpdate
    public void setUpdateDate() {
        this.updatedOn = LocalDate.now();
    }
}
