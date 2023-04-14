package com.rempler.factori20.api.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractAbstractContainerMenu extends AbstractContainerMenu {
    protected AbstractAbstractContainerMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
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
