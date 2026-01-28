package com.investment.goldsilver.service;

import com.investment.goldsilver.dto.DashboardStats;
import com.investment.goldsilver.dto.InvestmentDto;
import com.investment.goldsilver.entity.DailyPrice;
import com.investment.goldsilver.entity.Investment;
import com.investment.goldsilver.entity.MetalPrice;
import com.investment.goldsilver.entity.MetalType;
import com.investment.goldsilver.repository.DailyPriceRepository;
import com.investment.goldsilver.repository.InvestmentRepository;
import com.investment.goldsilver.repository.MetalPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final MetalPriceRepository metalPriceRepository;
    private final DailyPriceRepository dailyPriceRepository;

    /**
     * Get all investments ordered by date (newest first)
     */
    public List<Investment> getAllInvestments() {
        return investmentRepository.findAllByOrderByPurchaseDateDesc();
    }

    /**
     * Get investments by metal type
     */
    public List<Investment> getInvestmentsByMetalType(MetalType metalType) {
        return investmentRepository.findByMetalType(metalType);
    }

    /**
     * Get investments by metal type as DTOs with profit/loss calculations
     * Uses the price stored with each investment (not current market price)
     */
    public List<InvestmentDto> getInvestmentDtosByMetalType(MetalType metalType) {
        List<Investment> investments = investmentRepository.findByMetalType(metalType);

        // Convert to DTOs - each investment has its own stored price
        return investments.stream()
                .map(inv -> InvestmentDto.fromEntity(inv, null))
                .collect(Collectors.toList());
    }

    /**
     * Get investment by ID
     */
    public Optional<Investment> getInvestmentById(Long id) {
        return investmentRepository.findById(id);
    }

    /**
     * Save a new investment
     */
    @Transactional
    public Investment saveInvestment(Investment investment) {
        log.info("Saving investment: {} {} worth ₹{}",
                investment.getGrams(), investment.getMetalType(), investment.getAmount());
        return investmentRepository.save(investment);
    }

    /**
     * Update an existing investment
     */
    @Transactional
    public Investment updateInvestment(Long id, Investment investment) {
        Investment existing = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id));

        existing.setMetalType(investment.getMetalType());
        existing.setPurchaseDate(investment.getPurchaseDate());
        existing.setAmount(investment.getAmount());
        existing.setGrams(investment.getGrams());
        existing.setTodayPricePerGram(investment.getTodayPricePerGram());

        return investmentRepository.save(existing);
    }

    /**
     * Delete an investment
     */
    @Transactional
    public void deleteInvestment(Long id) {
        log.info("Deleting investment with id: {}", id);
        investmentRepository.deleteById(id);
    }

    /**
     * Calculate comprehensive dashboard statistics
     * Uses stored prices from each investment (not current market prices)
     */
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        // Get all investments
        List<Investment> goldInvestments = investmentRepository.findByMetalType(MetalType.GOLD);
        List<Investment> silverInvestments = investmentRepository.findByMetalType(MetalType.SILVER);

        // Calculate gold stats
        BigDecimal goldInvested = goldInvestments.stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal goldGrams = goldInvestments.stream()
                .map(Investment::getGrams)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal goldCurrentValue = goldInvestments.stream()
                .map(inv -> inv.getGrams().multiply(inv.getTodayPricePerGram()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal goldProfitLoss = goldCurrentValue.subtract(goldInvested);

        // Calculate silver stats
        BigDecimal silverInvested = silverInvestments.stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal silverGrams = silverInvestments.stream()
                .map(Investment::getGrams)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal silverCurrentValue = silverInvestments.stream()
                .map(inv -> inv.getGrams().multiply(inv.getTodayPricePerGram()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal silverProfitLoss = silverCurrentValue.subtract(silverInvested);

        // Calculate totals
        BigDecimal totalInvested = goldInvested.add(silverInvested);
        BigDecimal totalCurrentValue = goldCurrentValue.add(silverCurrentValue);
        BigDecimal totalProfitLoss = goldProfitLoss.add(silverProfitLoss);

        // Get current market prices (for display only, not for calculations)
        Optional<MetalPrice> goldPriceOpt = metalPriceRepository.findByMetalType(MetalType.GOLD);
        Optional<MetalPrice> silverPriceOpt = metalPriceRepository.findByMetalType(MetalType.SILVER);

        // Set all stats
        stats.setTotalInvested(totalInvested);
        stats.setGoldInvested(goldInvested);
        stats.setSilverInvested(silverInvested);
        stats.setTotalGoldGrams(goldGrams);
        stats.setTotalSilverGrams(silverGrams);
        stats.setGoldCurrentValue(goldCurrentValue);
        stats.setSilverCurrentValue(silverCurrentValue);
        stats.setTotalCurrentValue(totalCurrentValue);
        stats.setGoldProfitLoss(goldProfitLoss);
        stats.setSilverProfitLoss(silverProfitLoss);
        stats.setProfitLoss(totalProfitLoss);

        // Set current market prices (for reference)
        goldPriceOpt.ifPresent(price -> stats.setGoldCurrentPrice(price.getPricePerGram()));
        silverPriceOpt.ifPresent(price -> stats.setSilverCurrentPrice(price.getPricePerGram()));

        // Calculate profit/loss percentage
        if (totalInvested.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percentage = totalProfitLoss
                    .divide(totalInvested, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            stats.setProfitLossPercentage(percentage);
        }

        return stats;
    }

    /**
     * Get current price for a metal
     */
    public Optional<MetalPrice> getCurrentPrice(MetalType metalType) {
        return metalPriceRepository.findByMetalType(metalType);
    }

    /**
     * Update metal price (also saves to daily price history)
     */
    @Transactional
    public MetalPrice updateMetalPrice(MetalType metalType, BigDecimal pricePerGram) {
        Optional<MetalPrice> existingPrice = metalPriceRepository.findByMetalType(metalType);

        MetalPrice metalPrice;
        if (existingPrice.isPresent()) {
            metalPrice = existingPrice.get();
            metalPrice.setPricePerGram(pricePerGram);
        } else {
            metalPrice = new MetalPrice();
            metalPrice.setMetalType(metalType);
            metalPrice.setPricePerGram(pricePerGram);
        }

        log.info("Updating {} price to ₹{} per gram", metalType, pricePerGram);
        MetalPrice saved = metalPriceRepository.save(metalPrice);

        // Also save to daily price history
        saveDailyPrice(metalType, pricePerGram, LocalDate.now());

        return saved;
    }

    /**
     * Get daily price history for a metal type
     */
    public List<DailyPrice> getDailyPriceHistory(MetalType metalType) {
        return dailyPriceRepository.findByMetalTypeOrderByPriceDateAsc(metalType);
    }

    /**
     * Save or update daily price
     */
    @Transactional
    public DailyPrice saveDailyPrice(MetalType metalType, BigDecimal pricePerGram, LocalDate date) {
        Optional<DailyPrice> existing = dailyPriceRepository.findByMetalTypeAndPriceDate(metalType, date);

        DailyPrice dailyPrice;
        if (existing.isPresent()) {
            dailyPrice = existing.get();
            dailyPrice.setPricePerGram(pricePerGram);
        } else {
            dailyPrice = new DailyPrice();
            dailyPrice.setMetalType(metalType);
            dailyPrice.setPriceDate(date);
            dailyPrice.setPricePerGram(pricePerGram);
        }

        return dailyPriceRepository.save(dailyPrice);
    }

    /**
     * Delete daily price entry
     */
    @Transactional
    public void deleteDailyPrice(Long id) {
        dailyPriceRepository.deleteById(id);
    }
}
