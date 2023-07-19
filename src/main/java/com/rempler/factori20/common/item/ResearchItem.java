package com.rempler.factori20.common.item;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ResearchItem extends Item {
    private final int tier;
    private final int temp;
    public ResearchItem(Properties properties, int tier, int temp) {
        super(properties.stacksTo(64));
        this.tier = tier;
        this.temp = temp;
    }

    @Override
    public Component getName(ItemStack pStack) {
        String name;
        switch (this.tier) {
            case 1 -> name = "item.factori20.research_1";
            case 2 -> name = "item.factori20.research_2";
            case 3 -> name = "item.factori20.research_3";
            case 4 -> name = "item.factori20.research_4";
            case 5 -> name = "item.factori20.research_5";
            case 6 -> name = "item.factori20.research_6";
            default -> name = "item.factori20.research";
        }
        switch (this.temp) {
            case 1 -> name += "_1";
            case 8 -> name += "_8";
            case 64 -> name += "_64";
            default -> name += "";
        }
        return Component.translatable(name);
    }

    public int getTemp() {
        return temp;
    }

    public ResearchItem getItem(int temp, int tier) {
        for (Map.Entry<ResourceKey<Item>, Item> item : ForgeRegistries.ITEMS.getEntries().stream().toList()) {
            if (item.getValue() instanceof ResearchItem researchItem) {
                if (researchItem.getTemp() == temp && researchItem.getTier() == tier) {
                    return researchItem;
                }
            }
        }
        return null;
    }

    public int getTier() {
        return tier;
    }
}
