package com.rempler.factori20.api.chunk;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.ChunkDataEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        } else {
            LevelAccessor level = chunk.getWorldForge();
            if (level != null && !level.isClientSide()) {
                data = generateChunkResourceData(new Random());
                chunkResourceDataMap.put(chunkPos, data);
            }
        }
    }

    @SubscribeEvent
    public static void onServerClose(ServerStoppedEvent event) {
        chunkResourceDataMap.clear();
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
            LevelAccessor level = chunk.getWorldForge();
            if (level != null && !level.isClientSide()) {
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
        Map<String, Map<String, Integer>> resourceTypes = ResourceConfig.getResourceTypes();
        List<String> lst = new ArrayList<>(resourceTypes.keySet());
        if (lst.isEmpty()) {
            return data;
        }
        String firstResourceType = lst.stream().toList().get(random.nextInt(lst.size()));
        lst.add(firstResourceType);
        String secondResourceType;
        do {
            secondResourceType = lst.stream().toList().get(random.nextInt(lst.size()));
        } while (firstResourceType.equals(secondResourceType));
        lst.add(secondResourceType);

        Iterator<String> iterator = lst.iterator();

        while (iterator.hasNext()) {
            String resourceType = iterator.next();
            if (resourceTypes.containsKey(resourceType)) {
                int random1;
                int rarity = resourceTypes.get(resourceType).get("rarity");
                if (rarity == 0) {
                    random1 = new Random().nextInt(0, 500);
                } else if (rarity == 1) {
                    random1 = new Random().nextInt(0, 50);
                } else if (rarity == 2) {
                    random1 = new Random().nextInt(0, 25);
                } else if (rarity == 100) {
                    random1 = new Random().nextInt(0, 100);
                } else if (rarity > 20) {
                    random1 = new Random().nextInt(0, rarity);
                } else {
                    random1 = new Random().nextInt(0, 50);
                }
                if (random1 > 20) {
                    int amount = random.nextInt(resourceTypes.get(resourceType).get("amount") + 1);
                    data.setResourceAmount(resourceType, amount);
                } else if (data.getResourceAmount(resourceType) != 0) {
                    iterator.remove();
                } else {
                    data.setResourceAmount(resourceType, 0);
                    iterator.remove();
                }
            }
        }
        return data;
    }
}
