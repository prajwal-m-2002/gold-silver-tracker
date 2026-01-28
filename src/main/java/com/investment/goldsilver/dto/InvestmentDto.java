package com.investment.goldsilver.dto;

import com.investment.goldsilver.entity.Investment;
import com.investment.goldsilver.entity.MetalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for Investment with calculated fields (today's rate, current value,
 * profit/loss)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDto {
    private Long id;
    private MetalType metalType;
    private LocalDate purchaseDate;
    private BigDecimal amount;
    private BigDecimal grams;

    // Calculated fields (not stored in DB)
    private BigDecimal todayRate; // Current market price for this metal
    private BigDecimal currentValue; // grams × todayRate
    private BigDecimal profitLoss; // currentValue - amount

    /**
     * Create DTO from Investment entity
     * Uses the price stored with the investment (price at time of purchase)
     */
    public static InvestmentDto fromEntity(Investment investment, BigDecimal currentMarketPrice) {
        InvestmentDto dto = new InvestmentDto();
        dto.setId(investment.getId());
        dto.setMetalType(investment.getMetalType());
        dto.setPurchaseDate(investment.getPurchaseDate());
        dto.setAmount(investment.getAmount());
        dto.setGrams(investment.getGrams());

        // Use the price that was entered when the investment was made
        dto.setTodayRate(investment.getTodayPricePerGram());

        // Calculate current value and profit/loss using stored price
        if (investment.getTodayPricePerGram() != null) {
            BigDecimal currentValue = investment.getGrams().multiply(investment.getTodayPricePerGram());
            dto.setCurrentValue(currentValue);
            dto.setProfitLoss(currentValue.subtract(investment.getAmount()));
        }

        return dto;
    }

    /**
     * Check if this investment is profitable
     */
    public boolean isProfit() {
        return profitLoss != null && profitLoss.compareTo(BigDecimal.ZERO) > 0;
    }
}
