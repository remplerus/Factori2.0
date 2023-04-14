package com.rempler.factori20.api.chunk;

import com.rempler.factori20.utils.F20Config;

public enum ResourceType {
    COPPER("Copper", 0, F20Config.max_copper.get()),
    TIN("Tin", 0, F20Config.max_tin.get()),
    LEAD("Lead", 0, F20Config.max_lead.get()),
    SILICON("Silicon", 0, F20Config.max_silicon.get()),
    OIL("Oil", 0, F20Config.max_oil.get()),
    IRON("Iron", 0, F20Config.max_iron.get()),
    DIAMOND("Diamond", 0, F20Config.max_diamond.get()),
    GOLD("Gold", 0, F20Config.max_gold.get()),
    EMERALD("Emerald", 0, F20Config.max_emerald.get()),
    NATURAL_GAS("Natural Gas", 0, F20Config.max_natural_gas.get());

    private final String displayName;
    private final int minAmount;
    private final int maxAmount;

    ResourceType(String displayName, int minAmount, int maxAmount) {
        this.displayName = displayName;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
