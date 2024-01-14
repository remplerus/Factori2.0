package com.rempler.factori20.api.chunk;

import com.rempler.factori20.common.init.F20Attachments;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkResourceData implements INBTSerializable<CompoundTag> {
    private final Map<String, Integer> resources;

    public ChunkResourceData() {
        resources = new HashMap<>();
    }

    public int getResourceAmount(String resourceType) {
        return resources.getOrDefault(resourceType, 0);
    }

    public void setResourceAmount(String resourceType, int amount) {
        resources.put(resourceType, amount);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            nbt.putInt(entry.getKey(), entry.getValue());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (String resourceType : nbt.getAllKeys()) {
            if (nbt.contains(resourceType)) {
                resources.put(resourceType, nbt.getInt(resourceType));
            }
        }
    }

    public static ChunkResourceData get(Level level, BlockPos pos) {
        LevelChunk chunk = level.getChunkAt(pos);
        return chunk.getData(F20Attachments.CHUNK_RESOURCES.get());
    }

    public void removeResource(String resourceType, int amount) {
        int currentAmount = getResourceAmount(resourceType);
        setResourceAmount(resourceType, Math.max(currentAmount - amount, 0));
    }

    public String getRandomResource(RandomSource random) {
        List<String> availableResources = new ArrayList<>(resources.keySet());
        if (availableResources.isEmpty()) {
            return null;
        }

        return availableResources.get(random.nextInt(availableResources.size()));
    }
}
