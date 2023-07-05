package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.api.energy.F20EnergyStorage;
import com.rempler.factori20.api.helpers.MessagesHelper;
import com.rempler.factori20.api.sync.EnergySyncS2CPacket;
import com.rempler.factori20.common.abstractions.BaseResearchBlockEntity;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.ElectricResearchMenu;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class ElectricResearchBlockEntity extends BaseResearchBlockEntity {
    private final F20EnergyStorage energyStorage = new F20EnergyStorage(F20Config.researchEnergyStorage.get(), F20Config.researchReceive.get()) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            MessagesHelper.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private static final int ENERGY_REQ = 25;//TODO F20Config.researchCost.get();

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public ElectricResearchBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.ELECTRIC_RESEARCH.get(), pPos, pBlockState);
    }



    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player player) {
        return new ElectricResearchMenu(pContainerId, pInventory, this, this.data);
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
        return pEntity.energyStorage.getEnergyStored() >= ENERGY_REQ * getDrillSpeed(pEntity);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
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

        if (hasEnoughEnergy(pEntity) && shouldDrill(level, pos) && canInsert(pEntity)) {
            tickServer(level, pos, state, pEntity);
            extractEnergy(pEntity);
            setChanged(level, pos, state);
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

    }
}
