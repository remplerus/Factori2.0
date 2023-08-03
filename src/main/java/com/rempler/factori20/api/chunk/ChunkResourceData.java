package com.rempler.factori20.api.chunk;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ChunkResourceData implements INBTSerializable<CompoundTag> {
    private Map<ResourceType, Integer> resources;

    public ChunkResourceData() {
        resources = new EnumMap<>(ResourceType.class);
    }

    public int getResourceAmount(ResourceType resourceType) {
        return resources.getOrDefault(resourceType, 0);
    }

    public void setResourceAmount(ResourceType resourceType, int amount) {
        resources.put(resourceType, amount);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
            nbt.putInt(entry.getKey().name(), entry.getValue());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (ResourceType resourceType : ResourceType.values()) {
            if (nbt.contains(resourceType.name())) {
                resources.put(resourceType, nbt.getInt(resourceType.name()));
            }
        }
    }

    public static ChunkResourceData get(Level level, BlockPos pos) {
        LevelChunk chunk = level.getChunkAt(pos);
        return chunk.getCapability(ChunkResourceCapability.INSTANCE).orElse(null);
    }

    public void removeResource(ResourceType resourceType, int amount) {
        int currentAmount = getResourceAmount(resourceType);
        setResourceAmount(resourceType, Math.max(currentAmount - amount, 0));
    }

    public ResourceType getRandomResource(RandomSource random) {
        List<ResourceType> availableResources = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            if (getResourceAmount(resourceType) > 0) {
                availableResources.add(resourceType);
            }
        }

        if (availableResources.isEmpty()) {
            return null;
        }

        return availableResources.get(random.nextInt(availableResources.size()));
    }
}
