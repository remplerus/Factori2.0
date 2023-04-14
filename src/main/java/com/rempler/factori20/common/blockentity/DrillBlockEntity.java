package com.rempler.factori20.common.blockentity;

import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.menu.DrillContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class DrillBlockEntity extends RandomizableContainerBlockEntity {
    public static final int SIZE = 9;
    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE);
    public DrillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(F20BEs.DRILL.get(), pPos, pBlockState);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> nnl = NonNullList.create();
        for (int i = 0; i < SIZE; i++) {
            nnl.add(itemStackHandler.getStackInSlot(i));
        }
        return nnl;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        for (int i = 0; i < SIZE; i++) {
            itemStackHandler.setStackInSlot(i, pItemStacks.get(i));
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("f20.container.drill");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new DrillContainerMenu(pContainerId, pInventory, this);
    }

    @Override
    public int getContainerSize() {
        return SIZE;
    }
}
