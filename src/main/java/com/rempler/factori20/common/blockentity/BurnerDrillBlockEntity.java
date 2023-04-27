package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.common.abstractions.BaseDrillBlockEntity;
import com.rempler.factori20.common.abstractions.BaseDrillContainerMenu;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.BurnerDrillContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BurnerDrillBlockEntity extends BaseDrillBlockEntity {
    private final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack, null) > 0;
        }
    };
    private int burnTime;
    private int itemBurnTime;
    public BurnerDrillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.BURNER_DRILL.get(), pPos, pBlockState);
    }

    @Override
    public void tickServer() {
        if (level == null ||level.isClientSide) {
            return;
        }

        super.tickServer();
        if (burnTime > 0) {
            burnTime--;
            if (shouldDrill()) {
                performDrillingActions();
            }
        }

        if (burnTime <= 0 && shouldDrill()) {
            ItemStack fuelStack = fuelHandler.getStackInSlot(0);
            int burnTimeForItem = ForgeHooks.getBurnTime(fuelStack, null);
            if (burnTimeForItem > 0) {
                burnTime = burnTimeForItem;
                itemBurnTime = burnTimeForItem;
                fuelStack.shrink(1);
            }
        }

        setChanged();
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new BurnerDrillContainerMenu(pContainerId, pInventory, this, inputHandler, outputHandler);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        burnTime = pTag.getInt("burnTime");
        itemBurnTime = pTag.getInt("itemBurnTime");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("burnTime", burnTime);
        pTag.putInt("itemBurnTime", itemBurnTime);
    }
}
