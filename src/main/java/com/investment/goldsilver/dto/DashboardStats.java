package com.investment.goldsilver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for dashboard statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private BigDecimal totalInvested;
    private BigDecimal totalGoldGrams;
    private BigDecimal totalSilverGrams;
    private BigDecimal goldCurrentValue;
    private BigDecimal silverCurrentValue;
    private BigDecimal totalCurrentValue;
    private BigDecimal profitLoss;
    private BigDecimal profitLossPercentage;
    private BigDecimal goldInvested;
    private BigDecimal silverInvested;
    private BigDecimal goldCurrentPrice;
    private BigDecimal silverCurrentPrice;
    private BigDecimal goldProfitLoss;
    private BigDecimal silverProfitLoss;

    /**
     * Check if user is in profit
     */
    public boolean isProfit() {
        return profitLoss != null && profitLoss.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if prices are available
     */
    public boolean arePricesAvailable() {
        return goldCurrentPrice != null && silverCurrentPrice != null;
    }
}
