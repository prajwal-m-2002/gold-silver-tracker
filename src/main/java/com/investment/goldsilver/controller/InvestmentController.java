package com.investment.goldsilver.controller;

import com.investment.goldsilver.dto.DashboardStats;
import com.investment.goldsilver.entity.DailyPrice;
import com.investment.goldsilver.entity.Investment;
import com.investment.goldsilver.entity.MetalType;
import com.investment.goldsilver.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InvestmentController {

    private final InvestmentService investmentService;

    /**
     * Dashboard - Home page
     */
    @GetMapping("/")
    public String dashboard(Model model) {
        DashboardStats stats = investmentService.getDashboardStats();
        model.addAttribute("stats", stats);
        return "dashboard";
    }

    /**
     * Gold Investments Page
     */
    @GetMapping("/gold")
    public String goldInvestments(Model model) {
        model.addAttribute("metalType", MetalType.GOLD);
        model.addAttribute("investments", investmentService.getInvestmentDtosByMetalType(MetalType.GOLD));
        model.addAttribute("priceHistory", investmentService.getDailyPriceHistory(MetalType.GOLD));

        // Calculate totals for gold
        BigDecimal totalGrams = investmentService.getInvestmentsByMetalType(MetalType.GOLD).stream()
                .map(Investment::getGrams)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmount = investmentService.getInvestmentsByMetalType(MetalType.GOLD).stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalGrams", totalGrams);
        model.addAttribute("totalAmount", totalAmount);

        return "metal-investments";
    }

    /**
     * Silver Investments Page
     */
    @GetMapping("/silver")
    public String silverInvestments(Model model) {
        model.addAttribute("metalType", MetalType.SILVER);
        model.addAttribute("investments", investmentService.getInvestmentDtosByMetalType(MetalType.SILVER));
        model.addAttribute("priceHistory", investmentService.getDailyPriceHistory(MetalType.SILVER));

        // Calculate totals for silver
        BigDecimal totalGrams = investmentService.getInvestmentsByMetalType(MetalType.SILVER).stream()
                .map(Investment::getGrams)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmount = investmentService.getInvestmentsByMetalType(MetalType.SILVER).stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalGrams", totalGrams);
        model.addAttribute("totalAmount", totalAmount);

        return "metal-investments";
    }

    /**
     * Show add investment form
     */
    @GetMapping("/investments/add")
    public String showAddForm(@RequestParam(required = false) MetalType metalType, Model model) {
        Investment investment = new Investment();
        if (metalType != null) {
            investment.setMetalType(metalType);
        }
        model.addAttribute("investment", investment);
        model.addAttribute("metalTypes", MetalType.values());
        return "add-investment";
    }

    /**
     * Save new investment
     */
    @PostMapping("/investments/add")
    public String addInvestment(@Valid @ModelAttribute Investment investment,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("metalTypes", MetalType.values());
            return "add-investment";
        }

        investmentService.saveInvestment(investment);
        redirectAttributes.addFlashAttribute("successMessage",
                "Investment added successfully! " + investment.getGrams() + "g of " +
                        investment.getMetalType().getDisplayName());

        // Redirect to the metal-specific page
        return "redirect:/" + investment.getMetalType().name().toLowerCase();
    }

    /**
     * Show edit investment form
     */
    @GetMapping("/investments/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return investmentService.getInvestmentById(id)
                .map(investment -> {
                    model.addAttribute("investment", investment);
                    model.addAttribute("metalTypes", MetalType.values());
                    return "edit-investment";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Investment not found");
                    return "redirect:/";
                });
    }

    /**
     * Update investment
     */
    @PostMapping("/investments/edit/{id}")
    public String updateInvestment(@PathVariable Long id,
            @Valid @ModelAttribute Investment investment,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("metalTypes", MetalType.values());
            return "edit-investment";
        }

        Investment updated = investmentService.updateInvestment(id, investment);
        redirectAttributes.addFlashAttribute("successMessage", "Investment updated successfully!");

        // Redirect to the metal-specific page
        return "redirect:/" + updated.getMetalType().name().toLowerCase();
    }

    /**
     * Delete investment
     */
    @PostMapping("/investments/delete/{id}")
    public String deleteInvestment(@PathVariable Long id,
            @RequestParam String metalType,
            RedirectAttributes redirectAttributes) {
        try {
            investmentService.deleteInvestment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Investment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete investment");
        }
        return "redirect:/" + metalType.toLowerCase();
    }

    /**
     * Show price update page
     */
    @GetMapping("/prices")
    public String showPrices(Model model) {
        investmentService.getCurrentPrice(MetalType.GOLD)
                .ifPresent(price -> model.addAttribute("goldPrice", price.getPricePerGram()));
        investmentService.getCurrentPrice(MetalType.SILVER)
                .ifPresent(price -> model.addAttribute("silverPrice", price.getPricePerGram()));
        return "prices";
    }

    /**
     * Update prices
     */
    @PostMapping("/prices/update")
    public String updatePrices(@RequestParam BigDecimal goldPrice,
            @RequestParam BigDecimal silverPrice,
            RedirectAttributes redirectAttributes) {
        try {
            investmentService.updateMetalPrice(MetalType.GOLD, goldPrice);
            investmentService.updateMetalPrice(MetalType.SILVER, silverPrice);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Prices updated successfully! Gold: ₹" + goldPrice + " | Silver: ₹" + silverPrice);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update prices");
        }
        return "redirect:/prices";
    }

    /**
     * Add daily price entry
     */
    @PostMapping("/daily-price/add")
    public String addDailyPrice(@RequestParam MetalType metalType,
            @RequestParam BigDecimal pricePerGram,
            @RequestParam LocalDate priceDate,
            RedirectAttributes redirectAttributes) {
        try {
            investmentService.saveDailyPrice(metalType, pricePerGram, priceDate);
            redirectAttributes.addFlashAttribute("successMessage", "Daily price added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add daily price");
        }
        return "redirect:/" + metalType.name().toLowerCase();
    }

    /**
     * Delete daily price entry
     */
    @PostMapping("/daily-price/delete/{id}")
    public String deleteDailyPrice(@PathVariable Long id,
            @RequestParam String metalType,
            RedirectAttributes redirectAttributes) {
        try {
            investmentService.deleteDailyPrice(id);
            redirectAttributes.addFlashAttribute("successMessage", "Daily price deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete daily price");
        }
        return "redirect:/" + metalType.toLowerCase();
    }

    /**
     * API endpoint to get current metal price (for AJAX)
     */
    @GetMapping("/api/current-price")
    @ResponseBody
    public java.util.Map<String, Object> getCurrentPriceApi(@RequestParam MetalType metalType) {
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        BigDecimal price = investmentService.getCurrentPrice(metalType)
                .map(mp -> mp.getPricePerGram())
                .orElse(BigDecimal.ZERO);
        response.put("price", price);
        response.put("metalType", metalType.getDisplayName());
        return response;
    }
}
