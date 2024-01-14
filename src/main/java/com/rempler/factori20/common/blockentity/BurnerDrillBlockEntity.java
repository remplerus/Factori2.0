package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.api.common.bases.BaseDrillBlockEntity;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.BurnerDrillMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BurnerDrillBlockEntity extends BaseDrillBlockEntity {
    public final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return CommonHooks.getBurnTime(stack, null) > 0;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return CommonHooks.getBurnTime(stack, null)!=0 ? stack : super.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
    };

    public int burnTime = 0;
    public static int currentBurnTime;
    public int itemBurnTime = 0;
    public ContainerData burnData;
    public BurnerDrillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.BURNER_DRILL.get(), pPos, pBlockState);
        this.burnData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BurnerDrillBlockEntity.this.burnTime;
                    case 1 -> BurnerDrillBlockEntity.this.itemBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BurnerDrillBlockEntity.this.burnTime = pValue;
                    case 1 -> BurnerDrillBlockEntity.this.itemBurnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void drops() {
        super.drops();
        SimpleContainer inventory = new SimpleContainer(fuelHandler.getSlots());
        for (int i = 0; i < fuelHandler.getSlots(); i++) {
            inventory.setItem(i, fuelHandler.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    protected static boolean canInsertBurnable(BurnerDrillBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.fuelHandler.getSlots());
        boolean truege = true;

        for (int i = 0; i < blockEntity.fuelHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.fuelHandler.getStackInSlot(i));
            if (!canInsertAmountIntoOutputSlot(inventory, i) || !canInsertItemIntoOutputSlot(inventory, blockEntity.fuelHandler.getStackInSlot(i), i)) {
                truege = false;
            }
        }

        return truege;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BurnerDrillBlockEntity pEntity) {
        if (level.isClientSide) {
            return;
        }

        if (pEntity.burnTime <= 0) {
            ItemStack fuel = pEntity.fuelHandler.getStackInSlot(0);
            int burnTimeForItem = CommonHooks.getBurnTime(fuel, null);
            if (burnTimeForItem > 0) {
                pEntity.burnTime = burnTimeForItem;
                pEntity.itemBurnTime = burnTimeForItem;
                fuel.shrink(1);
            }
        }

        if (pEntity.burnTime > 0 && canInsert(pEntity) && shouldDrill(level, pos, pEntity)) {
            pEntity.burnTime--;
            tickServer(level, pos, state, pEntity);
            currentBurnTime = pEntity.burnTime;
        } else {
            setChanged(level, pos, state);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player player) {
        return new BurnerDrillMenu(pContainerId, pInventory, this, this.data, this.fuelHandler);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        burnTime = pTag.getInt("burnTime");
        itemBurnTime = pTag.getInt("itemBurnTime");
        fuelHandler.deserializeNBT(pTag.getCompound("burnItems"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        pTag.putInt("burnTime", burnTime);
        pTag.putInt("itemBurnTime", itemBurnTime);
        pTag.put("burnItems", fuelHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

}
