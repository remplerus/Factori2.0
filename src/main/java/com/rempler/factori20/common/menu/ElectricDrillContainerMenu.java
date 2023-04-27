package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.BaseDrillBlockEntity;
import com.rempler.factori20.common.abstractions.BaseDrillContainerMenu;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.ItemStackHandler;

public class ElectricDrillContainerMenu extends BaseDrillContainerMenu {
    public ElectricDrillContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        super(id, playerInventory, buf, F20Menus.ELECTRIC_DRILL_CONTAINER.get());
    }

    public ElectricDrillContainerMenu(int id, Inventory playerInventory, BaseDrillBlockEntity drillBlockEntity, ItemStackHandler inputHandler, ItemStackHandler outputHandler) {
        super(F20Menus.ELECTRIC_DRILL_CONTAINER.get(), id, playerInventory, drillBlockEntity, inputHandler, outputHandler);
    }
}
