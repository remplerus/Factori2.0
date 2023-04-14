package com.rempler.factori20.api.chunk;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.EnumMap;
import java.util.Map;

public class ChunkResourceData implements INBTSerializable<CompoundTag> {
    private final Map<ResourceType, Integer> resources;

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
        return chunk.getCapability(ChunkResourceDataProvider.INSTANCE).orElse(null);
    }
}
