package com.rempler.factori20.api.energy;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.function.IntSupplier;

public final class EnergyItem extends AbstractEnergyStorage implements IF20Energy {
    private final ItemStack stack;

    public EnergyItem(ItemStack stack, IntSupplier capacity) {
        super(capacity);
        this.stack = stack;
    }

    protected void writeEnergy() {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(F20Constants.ENERGY, getEnergyStoredCache());
    }

    protected void updateEnergy() {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(F20Constants.ENERGY))
            setEnergy(nbt.getInt(F20Constants.ENERGY));
        updateMaxEnergy();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    /**
     * Do not use {@link #extractEnergy(int, boolean)} internally. This method
     * stops the gadgets from being used like batteries.
     */
    public int extractEnergyPower(int maxExtract, boolean simulate) {
        if (maxExtract < 0)
            return 0;

        int energyExtracted = evaluateEnergyExtracted(maxExtract, simulate);
        if (! simulate) {
            setEnergy(getEnergyStored() - energyExtracted);
            writeEnergy();
        }
        return energyExtracted;
    }
}
