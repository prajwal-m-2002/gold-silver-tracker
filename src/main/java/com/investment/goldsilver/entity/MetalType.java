package com.investment.goldsilver.entity;

/**
 * Enum representing the type of precious metal
 */
public enum MetalType {
    GOLD("Gold", "#FFD700"),
    SILVER("Silver", "#C0C0C0");

    private final String displayName;
    private final String colorCode;

    MetalType(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }
}
