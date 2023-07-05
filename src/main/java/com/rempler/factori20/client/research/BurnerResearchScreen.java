package com.rempler.factori20.client.research;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rempler.factori20.client.abstractions.AbstractResearchScreen;
import com.rempler.factori20.common.menu.BurnerResearchMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BurnerResearchScreen extends AbstractResearchScreen<BurnerResearchMenu> {
    private final BurnerResearchMenu bMenu;
    public BurnerResearchScreen(BurnerResearchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/burner_research.png");
        this.bMenu = pMenu;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        int flameHeight = bMenu.getFlameScaled();
        this.blit(pPoseStack, leftPos + 26, topPos + 28 + 14 - flameHeight, 176, 13 - flameHeight, 14, flameHeight + 1);
    }

    //@Override
    //protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
    //    super.renderTooltip(pPoseStack, pX, pY);
    //    if (pX >= leftPos + 26 && pY >= topPos + 28 && pX <= leftPos + 26 + 14 && pY <= topPos + 28 + 14) {
    //        this.renderTooltip(pPoseStack, Component.translatable("txt.f20.burn_time", (bMenu. / 20)), pX, pY);
    //    }
    //}
}