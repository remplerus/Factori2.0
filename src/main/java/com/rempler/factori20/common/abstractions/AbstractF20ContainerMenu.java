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

        addPlayerInventory(playerInventory);
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

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j+i*9+9, 8+j*18, 86+i*18));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, 8+k*18, 144));
        }
    }
}
