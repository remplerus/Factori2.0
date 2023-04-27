package com.rempler.factori20.api.chunk;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkResourceCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ChunkResourceData> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){});
    private final ChunkResourceData data = new ChunkResourceData();
    private final LazyOptional<ChunkResourceData> dataOptional = LazyOptional.of(() -> data);


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return INSTANCE.orEmpty(cap, dataOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.deserializeNBT(nbt);
    }
}
