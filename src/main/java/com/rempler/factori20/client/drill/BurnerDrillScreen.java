package com.rempler.factori20.client.drill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rempler.factori20.client.abstractions.AbstractDrillScreen;
import com.rempler.factori20.common.abstractions.BaseDrillContainerMenu;
import com.rempler.factori20.common.menu.BurnerDrillContainerMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BurnerDrillScreen extends AbstractDrillScreen<BurnerDrillContainerMenu> {
    BurnerDrillContainerMenu bMenu;
    public BurnerDrillScreen(BurnerDrillContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/burner_drill.png");
        this.bMenu = pMenu;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);

        int flameHeight = bMenu.getFlameScaled();
        this.blit(pPoseStack, leftPos + 26, topPos + 29 + 14 - flameHeight, 176,  14 - flameHeight, 14, flameHeight);
    }
}
