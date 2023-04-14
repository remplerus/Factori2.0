package com.rempler.factori20.api.energy;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntSupplier;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final EnergyItem energyItem;
    private final LazyOptional<EnergyItem> energyCapability;

    public EnergyCapabilityProvider(ItemStack stack, IntSupplier energyCapability) {
        this.energyItem = new EnergyItem(stack, energyCapability);
        this.energyCapability = LazyOptional.of(() -> energyItem);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ENERGY ? energyCapability.cast() : LazyOptional.empty();
    }
}
