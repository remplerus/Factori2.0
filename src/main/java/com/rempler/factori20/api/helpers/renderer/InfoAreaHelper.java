package com.rempler.factori20.api.helpers.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public abstract class InfoAreaHelper extends GuiGraphics {
    protected final Rect2i area;

    protected InfoAreaHelper(Rect2i area) {
        //TODO: Fix this
        super(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        this.area = area;
    }

    public abstract void draw(GuiGraphics transform);
}
