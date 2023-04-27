package com.rempler.factori20.common.abstractions;

import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BaseDrillContainerMenu extends AbstractContainerMenu {
    private final BaseDrillBlockEntity drillBlockEntity;
    private final ContainerData data;

    public BaseDrillContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf, MenuType<?> menuType) {
        this(menuType, id, playerInventory, (BaseDrillBlockEntity) playerInventory.player.level.getBlockEntity(buf.readBlockPos()), new ItemStackHandler(1), new ItemStackHandler(9));
    }

    public BaseDrillContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory, BaseDrillBlockEntity drillBlockEntity, ItemStackHandler inputHandler, ItemStackHandler outputHandler) {
        super(menuType, id);
        this.drillBlockEntity = drillBlockEntity;
        this.data = new SimpleContainerData(4);

        addSlot(new SlotItemHandler(inputHandler, 0, 134, 35) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof DrillHeadItem;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlot(new SlotItemHandler(outputHandler, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

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
