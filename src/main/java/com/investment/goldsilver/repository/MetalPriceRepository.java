package com.investment.goldsilver.repository;

import com.investment.goldsilver.entity.MetalPrice;
import com.investment.goldsilver.entity.MetalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetalPriceRepository extends JpaRepository<MetalPrice, Long> {

    /**
     * Find current price for a specific metal type
     */
    Optional<MetalPrice> findByMetalType(MetalType metalType);
}
