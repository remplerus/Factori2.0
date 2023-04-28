package com.rempler.factori20.client.drill;

import com.rempler.factori20.client.abstractions.AbstractDrillScreen;
import com.rempler.factori20.common.menu.ElectricDrillContainerMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ElectricDrillScreen extends AbstractDrillScreen<ElectricDrillContainerMenu> {
    public ElectricDrillScreen(ElectricDrillContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/drill.png");
    }
}
