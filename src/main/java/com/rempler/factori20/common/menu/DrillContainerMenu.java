package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.blockentity.DrillBlockEntity;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class DrillContainerMenu extends AbstractContainerMenu {
    private final DrillBlockEntity tileEntity;
    private final ContainerData data;

    public DrillContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (DrillBlockEntity) playerInventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public DrillContainerMenu(int id, Inventory playerInventory, DrillBlockEntity tileEntity) {
        super(F20Menus.DRILL_CONTAINER_MENU.get(), id);
        this.tileEntity = tileEntity;
        this.data = new SimpleContainerData(4);

        tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    addSlot(new SlotItemHandler(h, j + i * 3, 62 + j * 18, 17 + i * 18));
                }
            }
        });

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
