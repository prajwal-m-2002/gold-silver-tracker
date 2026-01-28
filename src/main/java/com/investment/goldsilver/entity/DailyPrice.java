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
 * Entity representing daily price history for metals
 * Used to track price changes over time
 */
@Entity
@Table(name = "daily_prices", uniqueConstraints = @UniqueConstraint(columnNames = { "metal_type", "price_date" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Metal type is required")
    @Column(name = "metal_type", nullable = false)
    private MetalType metalType;

    @NotNull(message = "Price date is required")
    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    @NotNull(message = "Price per gram is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerGram;
}
