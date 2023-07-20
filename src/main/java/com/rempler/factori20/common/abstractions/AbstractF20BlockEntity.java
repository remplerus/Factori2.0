package com.rempler.factori20.common.abstractions;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractF20BlockEntity extends BlockEntity implements MenuProvider {
    public LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ContainerData data;
    public ItemStackHandler itemHandler;

    public AbstractF20BlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    protected static boolean canInsert(AbstractF20BlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots() - 1);
        boolean truege = true;

        for (int i = 0; i < blockEntity.itemHandler.getSlots() - 1; i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
            if (!canInsertAmountIntoOutputSlot(inventory, i) || !canInsertItemIntoOutputSlot(inventory, blockEntity.itemHandler.getStackInSlot(i), i)) {
                truege = false;
            }
            if (canInsertAmountIntoOutputSlot(inventory, i) && canInsertItemIntoOutputSlot(inventory, blockEntity.itemHandler.getStackInSlot(i), i)) {
                truege = true;
                break;
            }
        }

        return truege;
    }

    protected static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack, int i) {
        return inventory.getItem(i).getItem() == stack.getItem() || inventory.getItem(i).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int i) {
        return inventory.getItem(i).getMaxStackSize() > inventory.getItem(i).getCount();
    }

}
