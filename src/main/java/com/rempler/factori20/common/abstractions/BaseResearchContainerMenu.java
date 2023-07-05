package com.rempler.factori20.common.abstractions;

import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class BaseResearchContainerMenu extends AbstractContainerMenu {
    protected final BaseResearchBlockEntity ebe;

    public BaseResearchContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory, BaseResearchBlockEntity ebe) {
        super(menuType, id);
        this.ebe = ebe;

        this.ebe.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 9, 135, 35) {
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
                    addSlot(new SlotItemHandler(handler, j + i * 3, 62 + j * 18, 17 + i * 18));
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
