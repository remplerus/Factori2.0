package com.rempler.factori20.common.abstractions.bases;

import com.rempler.factori20.common.abstractions.AbstractF20ContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class BaseResearchContainerMenu extends AbstractF20ContainerMenu {

    public BaseResearchContainerMenu(MenuType<?> menuType, int id, Inventory playerInventory) {
        super(menuType, id, playerInventory);
    }
}
