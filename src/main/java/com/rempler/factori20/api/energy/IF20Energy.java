package com.rempler.factori20.api.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IF20Energy extends IEnergyStorage {
    int extractEnergyPower(int maxExtract, boolean simulate);
}
