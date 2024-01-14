package com.rempler.factori20.api.chunk;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class ChunkResourceCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ChunkResourceData data = new ChunkResourceData();

    @Override
    public CompoundTag serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.deserializeNBT(nbt);
    }

    @Nullable
    @Override
    public Object getCapability(Object o, Object o2) {
        return null;
    }
}
