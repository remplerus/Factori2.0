package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class VisualizerContainerMenu extends AbstractContainerMenu {
    private final ContainerData data;

    public VisualizerContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory);
    }

    public VisualizerContainerMenu(int id, Inventory playerInventory) {
        super(F20Menus.VISUALIZER_MENU.get(), id);
        this.data = new SimpleContainerData(1);

        // Player inventory
        int xPos = 8;
        int yPos = 84;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, xPos + col * 18, yPos + row * 18));
            }
        }

        // Player hotbar
        yPos = 142;
        for (int col = 0; col < 9; ++col) {
            addSlot(new Slot(playerInventory, col, xPos + col * 18, yPos));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            int containerSlots = slots.size() - playerIn.getInventory().getContainerSize();

            if (index < containerSlots) {
                if (!moveItemStackTo(itemstack1, containerSlots, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}