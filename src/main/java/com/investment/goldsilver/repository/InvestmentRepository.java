package com.investment.goldsilver.repository;

import com.investment.goldsilver.entity.Investment;
import com.investment.goldsilver.entity.MetalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    /**
     * Find all investments by metal type
     */
    List<Investment> findByMetalType(MetalType metalType);

    /**
     * Find all investments ordered by purchase date (newest first)
     */
    List<Investment> findAllByOrderByPurchaseDateDesc();

    /**
     * Calculate total amount invested for a specific metal
     */
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Investment i WHERE i.metalType = :metalType")
    BigDecimal getTotalInvestedByMetalType(MetalType metalType);

    /**
     * Calculate total grams purchased for a specific metal
     */
    @Query("SELECT COALESCE(SUM(i.grams), 0) FROM Investment i WHERE i.metalType = :metalType")
    BigDecimal getTotalGramsByMetalType(MetalType metalType);

    /**
     * Get total invested across all metals
     */
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Investment i")
    BigDecimal getTotalInvested();
}
