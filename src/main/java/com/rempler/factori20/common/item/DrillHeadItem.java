package com.rempler.factori20.common.item;

import com.rempler.factori20.api.helpers.ColorHelper;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.world.item.Item;

public class DrillHeadItem extends Item {
    private final int tier;
    private final int color;

    public DrillHeadItem(Properties properties, int tier, int color) {
        super(properties.stacksTo(1));
        this.tier = tier;
        this.color = color;
    }

    public int getSpeed() {
        return switch (this.tier) {
            case 1 -> F20Config.iron_speed.get();
            case 2 -> F20Config.gold_speed.get();
            case 3 -> F20Config.diamond_speed.get();
            case 4 -> F20Config.emerald_speed.get();
            default -> 0;
        };
    }
}
