package com.rempler.factori20.api.common.bases;

import com.rempler.factori20.api.common.AbstractF20ContainerMenu;
import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class BaseDrillContainerMenu extends AbstractF20ContainerMenu {
    protected final BaseDrillBlockEntity ebe;

    public BaseDrillContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory, BaseDrillBlockEntity ebe) {
        super(menuType, id, playerInventory);
        this.ebe = ebe;

        this.ebe.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 135, 35) {
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
                    addSlot(new SlotItemHandler(handler, 1 + j + i * 3, 62 + j * 18, 17 + i * 18) {
                        @Override
                        public boolean mayPlace(ItemStack pStack) {
                            return false;
                        }

                        @Override
                        public int getMaxStackSize() {
                            return 64;
                        }
                    });
                }
            }
        });
    }
}