package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.BaseDrillContainerMenu;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.ItemStackHandler;

public class ElectricDrillContainerMenu extends BaseDrillContainerMenu {
    public ElectricDrillContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (ElectricDrillBlockEntity) playerInventory.player.level.getBlockEntity(buf.readBlockPos()), new ItemStackHandler(1), new ItemStackHandler(9));
    }

    public ElectricDrillContainerMenu(int id, Inventory playerInventory, ElectricDrillBlockEntity drillBlockEntity, ItemStackHandler inputHandler, ItemStackHandler outputHandler) {
        super(F20Menus.ELECTRIC_DRILL_CONTAINER.get(), id, playerInventory, inputHandler, outputHandler);
    }
}
