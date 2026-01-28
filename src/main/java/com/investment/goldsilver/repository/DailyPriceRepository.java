package com.investment.goldsilver.repository;

import com.investment.goldsilver.entity.DailyPrice;
import com.investment.goldsilver.entity.MetalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyPriceRepository extends JpaRepository<DailyPrice, Long> {

    /**
     * Find all daily prices for a specific metal, ordered by date (oldest first,
     * newest at bottom)
     */
    List<DailyPrice> findByMetalTypeOrderByPriceDateAsc(MetalType metalType);

    /**
     * Find price for a specific metal on a specific date
     */
    Optional<DailyPrice> findByMetalTypeAndPriceDate(MetalType metalType, LocalDate priceDate);

    /**
     * Find latest price for a specific metal
     */
    Optional<DailyPrice> findFirstByMetalTypeOrderByPriceDateDesc(MetalType metalType);
}
