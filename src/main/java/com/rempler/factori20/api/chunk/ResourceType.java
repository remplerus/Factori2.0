package com.rempler.factori20.api.chunk;

import com.rempler.factori20.utils.F20Config;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum ResourceType {
    COPPER(Items.COPPER_ORE, 100, F20Config.max_copper.get()),
    //TIN("Tin", 0, F20Config.max_tin.get()),
    //LEAD("Lead", 0, F20Config.max_lead.get()),
    //SILICON("Silicon", 0, F20Config.max_silicon.get()),
    //OIL("Oil", 0, F20Config.max_oil.get()),
    IRON(Items.IRON_ORE, 0, F20Config.max_iron.get()),
    DIAMOND(Items.DIAMOND, 0, F20Config.max_diamond.get()),
    GOLD(Items.GOLD_ORE, 0, F20Config.max_gold.get()),
    //NATURAL_GAS("Natural Gas", 0, F20Config.max_natural_gas.get()),
    EMERALD(Items.EMERALD, 0, F20Config.max_emerald.get());

    private final Item item;
    private int minAmount;
    private final int maxAmount;

    ResourceType(Item item, int minAmount, int maxAmount) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public Item getItem() {
        return item;
    }
    public String getName() { return item.toString(); }

    public int getMinAmount() {
        return minAmount;
    }
    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
