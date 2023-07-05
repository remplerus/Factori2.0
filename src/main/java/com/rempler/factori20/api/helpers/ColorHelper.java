package com.rempler.factori20.api.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorHelper {

    public static int getColorFrom(ResourceLocation location) {
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS);
        if (texture instanceof TextureAtlas && !(location.getPath().equals("grass_block"))) {
            return getColorFrom(((TextureAtlas) texture).getSprite(location));
        }
        if (location.getPath().equals("grass_block")) {
            return 0xFF00FF00;
        }
        return 0;
    }

    public static int getColorFrom(TextureAtlasSprite sprite) {
        if (sprite == null) return -1;
        if (sprite.contents().getUniqueFrames().sum() == 0) return -1;
        if (sprite.atlasLocation().getPath().equals("missingno")) return -1;
        float total = 0, red = 0, blue = 0, green = 0;
        for (int x = 0; x < sprite.contents().width(); x++) {
            for (int y = 0; y < sprite.contents().height(); y++) {
                int color = sprite.getPixelRGBA(0, x, y);
                int alpha = color >> 24 & 0xFF;
                // if (alpha != 255) continue; // this creates problems for translucent textures
                total += alpha;
                red += (color & 0xFF) * alpha;
                green += (color >> 8 & 0xFF) * alpha;
                blue += (color >> 16 & 0xFF) * alpha;
            }
        }

        if (total > 0)
            return FastColor.ARGB32.color(255, (int) (red / total), (int) (green / total), (int) (blue / total));
        return -1;
    }
}
