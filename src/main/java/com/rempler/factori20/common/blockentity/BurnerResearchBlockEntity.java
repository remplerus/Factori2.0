package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.api.common.bases.BaseResearchBlockEntity;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.BurnerResearchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BurnerResearchBlockEntity extends BaseResearchBlockEntity {
    protected LazyOptional<IItemHandler> lazyFuelHandler = LazyOptional.empty();
    public final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack, null) > 0;
        }
    };

    public int burnTime = 0;
    public static int currentBurnTime;
    public int itemBurnTime = 0;
    public ContainerData burnData;
    public BurnerResearchBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.BURNER_RESEARCH.get(), pPos, pBlockState);
        this.burnData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BurnerResearchBlockEntity.this.burnTime;
                    case 1 -> BurnerResearchBlockEntity.this.itemBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BurnerResearchBlockEntity.this.burnTime = pValue;
                    case 1 -> BurnerResearchBlockEntity.this.itemBurnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BurnerResearchBlockEntity pEntity) {
        if (level.isClientSide) {
            return;
        }

        if (pEntity.burnTime <= 0) {
            ItemStack fuel = pEntity.fuelHandler.getStackInSlot(0);
            int burnTimeForItem = ForgeHooks.getBurnTime(fuel, null);
            if (burnTimeForItem > 0) {
                pEntity.burnTime = burnTimeForItem;
                pEntity.itemBurnTime = burnTimeForItem;
                fuel.shrink(1);
            }
        }

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        if (pEntity.burnTime > 0 && canInsert(pEntity) && pEntity.isRecipeThere(level, inventory)) {
            pEntity.burnTime--;
            tickServer(level, pEntity, inventory);
            currentBurnTime = pEntity.burnTime;
            setChanged(level, pos, state);
        } else {
            setChanged(level, pos, state);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null && lazyFuelHandler.isPresent()) {
                return lazyItemHandler.cast();
            } else {
                return lazyFuelHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFuelHandler = LazyOptional.of(() -> fuelHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFuelHandler.invalidate();
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player player) {
        return new BurnerResearchMenu(pContainerId, pInventory, this, this.data, this.fuelHandler, this.outputHandler);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        burnTime = pTag.getInt("burnTime");
        itemBurnTime = pTag.getInt("itemBurnTime");
        fuelHandler.deserializeNBT(pTag.getCompound("burnItems"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("burnTime", burnTime);
        pTag.putInt("itemBurnTime", itemBurnTime);
        pTag.put("burnItems", fuelHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

}
