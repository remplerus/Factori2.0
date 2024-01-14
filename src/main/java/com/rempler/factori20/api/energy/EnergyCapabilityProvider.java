package com.rempler.factori20.api.energy;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntSupplier;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final EnergyItem energyItem;
    private final Object energyCapability;

    public EnergyCapabilityProvider(ItemStack stack, IntSupplier energyCapability) {
        this.energyItem = new EnergyItem(stack, energyCapability);
        this.energyCapability = energyItem.getEnergyStored();
    }

    @Nullable
    @Override
    public Object getCapability(Object o, Object o2) {
        return o == Capabilities.EnergyStorage.ITEM ? energyCapability : null;
    }
}
