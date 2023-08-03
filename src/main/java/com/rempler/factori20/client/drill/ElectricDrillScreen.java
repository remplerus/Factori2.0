package com.rempler.factori20.client.drill;

import com.rempler.factori20.api.helpers.MouseHelper;
import com.rempler.factori20.api.helpers.renderer.EnergyInfoArea;
import com.rempler.factori20.client.abstractions.AbstractDrillScreen;
import com.rempler.factori20.common.menu.ElectricDrillMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ElectricDrillScreen extends AbstractDrillScreen<ElectricDrillMenu> {
    private EnergyInfoArea infoArea;
    public ElectricDrillScreen(ElectricDrillMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/drill.png");
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        infoArea = new EnergyInfoArea(x + 14, y + 14, menu.ebe.getEnergyStorage(), 4, 64);
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
        assignEnergyInfoArea();
        infoArea.draw(pPoseStack);
    }

    @Override
    protected void renderLabels(GuiGraphics pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyTooltips(GuiGraphics pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if (mouseIsAboveArea(pMouseX, pMouseY, x, y, 14, 14, 4, 62)) {
            renderTooltip(pPoseStack, pMouseX - x, pMouseY - y);
        }
    }

    private boolean mouseIsAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseHelper.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
