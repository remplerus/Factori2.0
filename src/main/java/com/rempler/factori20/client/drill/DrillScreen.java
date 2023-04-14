package com.rempler.factori20.client.drill;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rempler.factori20.common.menu.DrillContainerMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DrillScreen extends AbstractContainerScreen<DrillContainerMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/drill.png");

    public DrillScreen(DrillContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    //@Override
    //protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
    //    super.renderLabels(pPoseStack, pMouseX, pMouseY);
    //    this.font.draw(pPoseStack, this.title.getString(), 8F, 6F, 4210752);
    //    this.font.draw(pPoseStack, this.playerInventoryTitle.getString(), 8.0F, (float) (this.imageHeight - 96 + 2), 4210752);
    //}
}
