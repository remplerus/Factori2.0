package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.api.energy.AbstractEnergyStorage;
import com.rempler.factori20.common.abstractions.BaseDrillBlockEntity;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.BurnerDrillContainerMenu;
import com.rempler.factori20.common.menu.ElectricDrillContainerMenu;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;

public class ElectricDrillBlockEntity extends BaseDrillBlockEntity implements IEnergyStorage {
    private final AbstractEnergyStorage energyStorage;
    private CompoundTag energyTag;
    public ElectricDrillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.ELECTRIC_DRILL.get(), pPos, pBlockState);
        energyStorage = new AbstractEnergyStorage(F20Config.drillEnergyStorage::get, F20Config.drillReceive::get, F20Config.drillCost::get) {
            @Override
            public void writeEnergy() {
                if (energyTag == null) {
                    energyTag = new CompoundTag();
                }

                energyTag.putInt("energy", this.getEnergyStored());
            }

            @Override
            public void updateEnergy() {
                if (energyTag != null && energyTag.contains("energy")) {
                    this.setEnergy(energyTag.getInt("energy"));
                }
            }
        };
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        energyTag = pTag.getCompound("energyTag");
        energyStorage.updateEnergy();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        energyStorage.writeEnergy();
        if (energyTag != null) {
            pTag.put("energyTag", energyTag);
        }
    }

    @Override
    public void tickServer() {
        if (level != null && !level.isClientSide) {
            super.tickServer();
            if (energyStorage.getEnergyStored() >= F20Config.drillCost.get() && shouldDrill()) {
                performDrillingActions();
                energyStorage.extractEnergy(F20Config.drillCost.get(), false);
            }
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ElectricDrillContainerMenu(pContainerId, pInventory, this, inputHandler, outputHandler);
    }
}
