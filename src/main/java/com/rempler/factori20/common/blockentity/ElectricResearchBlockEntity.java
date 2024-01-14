package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.api.common.bases.BaseResearchBlockEntity;
import com.rempler.factori20.api.energy.F20EnergyStorage;
import com.rempler.factori20.api.helpers.MessagesHelper;
import com.rempler.factori20.api.sync.EnergySyncS2CPacket;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.ElectricResearchMenu;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ElectricResearchBlockEntity extends BaseResearchBlockEntity {
    private final F20EnergyStorage energyStorage = new F20EnergyStorage(F20Config.researchEnergyStorage.get(), F20Config.researchReceive.get()) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            //MessagesHelper.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private static final int ENERGY_REQ = F20Config.researchCost.get();

    public ElectricResearchBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.ELECTRIC_RESEARCH.get(), pPos, pBlockState);
    }



    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player player) {
        return new ElectricResearchMenu(pContainerId, pInventory, this, this.data, this.outputHandler);
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public void setEnergyLevel(int energy) {
        this.energyStorage.setEnergy(energy);
    }

    private static void extractEnergy(ElectricResearchBlockEntity pEntity) {
        pEntity.energyStorage.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(ElectricResearchBlockEntity pEntity) {
        return pEntity.energyStorage.getEnergyStored() >= ENERGY_REQ;
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("energy", energyStorage.getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        energyStorage.setEnergy(pTag.getInt("energy"));
    }


    public static void tick(Level level, BlockPos pos, BlockState state, ElectricResearchBlockEntity pEntity) {
        if (level.isClientSide) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        if (hasEnoughEnergy(pEntity) && canInsert(pEntity) && pEntity.isRecipeThere(level, inventory)) {
            tickServer(level, pEntity, inventory);
            extractEnergy(pEntity);
            setChanged(level, pos, state);
        } else {
            setChanged(level, pos, state);
        }
    }
}
