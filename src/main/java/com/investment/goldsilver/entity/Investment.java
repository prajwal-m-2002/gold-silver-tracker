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
 * Entity representing a single investment transaction
 * User manually enters the grams purchased
 */
@Entity
@Table(name = "investments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Metal type is required")
    @Column(nullable = false)
    private MetalType metalType;

    @NotNull(message = "Purchase date is required")
    @Column(nullable = false)
    private LocalDate purchaseDate;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Grams is required")
    @DecimalMin(value = "0.0001", message = "Grams must be at least 0.0001")
    @Column(nullable = false, precision = 10, scale = 5)
    private BigDecimal grams;

    @NotNull(message = "Today's price per gram is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal todayPricePerGram;
}
