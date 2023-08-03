package com.rempler.factori20.client.drill;

import com.rempler.factori20.client.abstractions.AbstractDrillScreen;
import com.rempler.factori20.common.menu.BurnerDrillMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BurnerDrillScreen extends AbstractDrillScreen<BurnerDrillMenu> {
    private final BurnerDrillMenu bMenu;
    public BurnerDrillScreen(BurnerDrillMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/burner_drill.png");
        this.bMenu = pMenu;
    }

    @Override
    public void render(GuiGraphics pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        int flameHeight = bMenu.getFlameScaled();
        pPoseStack.blit(BACKGROUND_TEXTURE, leftPos + 26, topPos + 28 + 14 - flameHeight, 176, 13 - flameHeight, 14, flameHeight + 1);
    }

    //@Override
    //protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
    //    super.renderTooltip(pPoseStack, pX, pY);
    //    if (pX >= leftPos + 26 && pY >= topPos + 28 && pX <= leftPos + 26 + 14 && pY <= topPos + 28 + 14) {
    //        this.renderTooltip(pPoseStack, Component.translatable("txt.f20.burn_time", (bMenu. / 20)), pX, pY);
    //    }
    //}
}
