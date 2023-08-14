package com.rempler.factori20.compat.eio.customapi;

import com.enderio.api.conduit.IConduitMenuData;
import com.enderio.api.conduit.TieredConduit;
import com.enderio.api.conduit.ticker.IConduitTicker;
import com.enderio.api.misc.Vector2i;
import com.enderio.conduits.common.types.EnergyExtendedData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CustomEnergyConduitType extends TieredConduit<EnergyExtendedData> {
    private final int transferRate;
    private final int tickRate;

    public CustomEnergyConduitType(ResourceLocation texture, int tier, ResourceLocation iconTexture, Vector2i iconTexturePos) {
        super(texture, new ResourceLocation(""), tier, iconTexture, iconTexturePos);
        this.transferRate = tier;
        this.tickRate = tier >= 100 ? 1 : tier;
    }

    @Override
    public IConduitTicker getTicker() {
        return new CustomEnergyConduitTicker(this.transferRate, this.tickRate);
    }

    @Override
    public IConduitMenuData getMenuData() {
        return IConduitMenuData.ENERGY;
    }

    @Override
    public EnergyExtendedData createExtendedConduitData(Level level, BlockPos blockPos) {
        return new EnergyExtendedData();
    }
}
