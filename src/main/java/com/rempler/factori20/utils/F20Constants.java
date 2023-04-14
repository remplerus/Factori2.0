package com.rempler.factori20.utils;

import com.rempler.factori20.common.init.F20Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class F20Constants {
    public static final String MODID = "factori20";
    public static CreativeModeTab F20_TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return F20Items.SCANNER.get().getDefaultInstance();
        }
    };
    public static final String ENERGY = "energy";
}
