package com.rempler.factori20.client.visualizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.api.chunk.ResourceType;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class VisualizerScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(F20Constants.MODID, "textures/gui/visualizer.png");
    private static final int MAP_RADIUS = 7;
    int imageWidth;
    int imageHeight;
    int backgroundX = (this.width - this.imageWidth) / 2;
    int backgroundY = (this.height - this.imageHeight) / 2;


    public VisualizerScreen(Component pTitle) {
        super(pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(pPoseStack, backgroundX, backgroundY, 0, 0, this.imageWidth, this.imageHeight);

        int startX = 56;
        int startZ = 13;
        int endX = 119;
        int endZ = 76;
        int mapWidth = endX - startX;
        int mapHeight = endZ - startZ;

        // Draw the map here

        Player player = Minecraft.getInstance().player;
        Level level = player.getLevel();
        BlockPos playerPos = player.blockPosition();

        int blockSize = 16;

        for(int xOffset = -MAP_RADIUS; xOffset <= MAP_RADIUS; xOffset++) {
            for(int zOffset = -MAP_RADIUS; zOffset <= MAP_RADIUS; zOffset++) {
                BlockPos currentPos = new BlockPos(playerPos.getX() + xOffset * 16, playerPos.getY(), playerPos.getZ() + zOffset * 16);

                // Render the top block here
                for(int x = 0; x < blockSize; x++) {
                    for (int z = 0; z < blockSize; z++) {
                        int offsetY = level.getHeight(Heightmap.Types.WORLD_SURFACE, currentPos.getX() + x, currentPos.getZ() + z) - 1;
                        BlockPos topBlockPos = currentPos.offset(x, offsetY - currentPos.getY(), z);
                        BlockState topBlockState = level.getBlockState(topBlockPos);

                        // Render the top block here
                        int color = Minecraft.getInstance().getBlockColors().getColor(topBlockState, level, topBlockPos, 0);
                        int xDraw = backgroundX + startX + (xOffset * 16 + x) * mapWidth / (blockSize * (MAP_RADIUS * 2 + 1));
                        int zDraw = backgroundY + startZ + (zOffset * 16 + z) * mapHeight / (blockSize * (MAP_RADIUS * 2 + 1));
                        fill(pPoseStack, xDraw, zDraw, xDraw + mapWidth / (blockSize * (MAP_RADIUS * 2 + 1)), zDraw + mapHeight / (blockSize * (MAP_RADIUS * 2 + 1)), color);

                        // Render the virtual ores and liquids here
                        ChunkResourceData resourceData = ChunkResourceData.get(level, currentPos);
                        for (ResourceType resourceType : ResourceType.values()) {
                            int amount = resourceData.getResourceAmount(resourceType);
                            if (amount > 0) {
                                // You can create custom textures for each resource type and render them here.
                                // For simplicity, we just render the amount as a string.
                                this.font.draw(pPoseStack, String.valueOf(amount), xDraw, zDraw, 0xFFFFFF);
                            }
                        }
                    }
                }
                int playerX = player.blockPosition().getX() - currentPos.getX();
                int playerZ = player.blockPosition().getZ() - currentPos.getZ();

                // Check if the player is within the visible map area
                if (playerX >= -MAP_RADIUS && playerX <= MAP_RADIUS && playerZ >= -MAP_RADIUS && playerZ <= MAP_RADIUS) {
                    // Draw the player point on the map
                    int playerPixelX = startX + (playerX + MAP_RADIUS) * blockSize + blockSize / 2;
                    int playerPixelY = startZ + (playerZ + MAP_RADIUS) * blockSize + blockSize / 2;
                    int playerPointColor = 0xFFFF0000; // Red color for the player point
                    fill(pPoseStack, playerPixelX - 1, playerPixelY - 1, playerPixelX + 1, playerPixelY + 1, playerPointColor);
                }
            }
        }

    }

    // Draw tooltips etc.
}
