package com.rempler.factori20.api.chunk;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = F20Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChunkResourceGenerator {
    public static final String CHUNK_RESOURCE_DATA_KEY = "chunk_resource_data";
    private static final Map<ChunkPos, ChunkResourceData> chunkResourceDataMap = new HashMap<>();

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();
        ChunkResourceData data = chunkResourceDataMap.get(chunkPos);

        if (data != null) {
            event.getData().put(CHUNK_RESOURCE_DATA_KEY, data.serializeNBT());
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        if (event.getData().contains(CHUNK_RESOURCE_DATA_KEY)) {
            ChunkResourceData data = new ChunkResourceData();
            data.deserializeNBT(event.getData().getCompound(CHUNK_RESOURCE_DATA_KEY));
            chunkResourceDataMap.put(chunkPos, data);
        } else {
            LevelAccessor world = chunk.getWorldForge();
            if (world != null && !world.isClientSide()) {
                ChunkResourceData data = generateChunkResourceData(new Random());
                chunkResourceDataMap.put(chunkPos, data);
            }
        }
    }

    public static ChunkResourceData getChunkResourceData(ChunkPos chunkPos) {
        return chunkResourceDataMap.get(chunkPos);
    }

    private static ChunkResourceData generateChunkResourceData(Random random) {
        ChunkResourceData data = new ChunkResourceData();
        for (ResourceType resourceType : ResourceType.values()) {
            int amount = random.nextInt(resourceType.getMaxAmount() - resourceType.getMinAmount() + 1) + resourceType.getMinAmount();
            data.setResourceAmount(resourceType, amount);
        }
        return data;
    }
}
