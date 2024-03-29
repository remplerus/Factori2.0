package com.rempler.factori20.api.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rempler.factori20.api.common.bases.BaseDrillContainerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public abstract class AbstractDrillScreen<T extends BaseDrillContainerMenu> extends AbstractContainerScreen<T> {
    protected static ResourceLocation BACKGROUND_TEXTURE;

    public AbstractDrillScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        //TODO: Fix this
        pPoseStack.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        Slot slotUnderMouse = this.getSlotUnderMouse();
        if (slotUnderMouse != null && slotUnderMouse.hasItem()) {
            //this.renderTooltip(pPoseStack, pX, pY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
