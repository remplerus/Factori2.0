package com.rempler.factori20.api.common.bases;

import com.rempler.factori20.api.common.AbstractF20ContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public abstract class BaseResearchContainerMenu extends AbstractF20ContainerMenu {
    protected final Level level;
    public BaseResearchBlockEntity ebe;
    protected final ContainerData data;

    public BaseResearchContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory, BaseResearchBlockEntity ebe, ItemStackHandler outputHandler, ContainerData data) {
        super(menuType, id, playerInventory);
        this.level = playerInventory.player.level();
        this.ebe = ebe;
        this.data = data;

        addSlot(new SlotItemHandler(outputHandler, 0, 135, 35));

        //TODO: Fix this
        //this.ebe.getCapability.getData().ifPresent(handler -> {
        //    for (int i = 0; i < handler.getSlots(); ++i) {
        //        addSlot(new SlotItemHandler(handler, i, 62 + 12, 17 + i * 18));
        //    }
        //});

        addDataSlots(data);
    }
}
