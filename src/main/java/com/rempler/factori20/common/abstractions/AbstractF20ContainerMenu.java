package com.rempler.factori20.common.abstractions;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractF20ContainerMenu extends AbstractContainerMenu {
    public AbstractF20ContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory) {
        super(menuType, id);

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
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack emptystack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return emptystack;
        ItemStack sourceStack = slot.getItem();
        ItemStack copySourceStack = sourceStack.copy();
        int containerSlots = slots.size() - playerIn.getInventory().getContainerSize();

        if (index < containerSlots) {
            if (!moveItemStackTo(sourceStack, containerSlots, slots.size(), false)) {
                return emptystack;
            }
        } else if (!moveItemStackTo(sourceStack, 0, containerSlots, false)) {
            return emptystack;
        }

        if (sourceStack.getCount() == 0) {
            slot.set(emptystack);
        } else {
            slot.setChanged();
        }

        slot.onTake(playerIn, sourceStack);

        return copySourceStack;
    }
}
