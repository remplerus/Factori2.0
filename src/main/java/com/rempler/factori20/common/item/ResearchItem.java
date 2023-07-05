package com.rempler.factori20.common.item;

import com.rempler.factori20.utils.F20Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ResearchItem extends Item {
    private final int tier;
    public ResearchItem(Properties properties, int tier) {
        super(properties.stacksTo(64));
        this.tier = tier;
    }

    @Override
    public Component getName(ItemStack pStack) {
        return switch (this.tier) {
            case 1 -> Component.translatable("item.factori20.research_1");
            case 2 -> Component.translatable("item.factori20.research_2");
            case 3 -> Component.translatable("item.factori20.research_3");
            case 4 -> Component.translatable("item.factori20.research_4");
            case 5 -> Component.translatable("item.factori20.research_5");
            case 6 -> Component.translatable("item.factori20.research_6");
            default -> Component.translatable("item.factori20.research");
        };
    }
}
