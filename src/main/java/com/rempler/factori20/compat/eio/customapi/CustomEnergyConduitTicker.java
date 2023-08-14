package com.rempler.factori20.compat.eio.customapi;

import com.enderio.api.conduit.IConduitType;
import com.enderio.api.conduit.ticker.CapabilityAwareConduitTicker;
import com.enderio.api.misc.ColorControl;
import com.enderio.conduits.common.types.EnergyConduitTicker;
import com.enderio.conduits.common.types.EnergyExtendedData;
import dev.gigaherz.graph3.Graph;
import dev.gigaherz.graph3.Mergeable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.energy.IEnergyStorage;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;

public class CustomEnergyConduitTicker extends EnergyConduitTicker {
    private final int transferRate;
    private final int tickRate;

    public CustomEnergyConduitTicker(int transferRate, int tickRate) {
        super();
        this.transferRate = transferRate;
        this.tickRate = tickRate;
    }

    @Override
    public void tickCapabilityGraph(IConduitType<?> type, List<CapabilityAwareConduitTicker<IEnergyStorage>.CapabilityConnection> inserts, List<CapabilityAwareConduitTicker<IEnergyStorage>.CapabilityConnection> extracts, ServerLevel level, Graph<Mergeable.Dummy> graph, TriFunction<ServerLevel, BlockPos, ColorControl, Boolean> isRedstoneActive) {
        toNextExtract:
        for (CapabilityConnection extract : extracts) {
            IEnergyStorage extractHandler = extract.cap;
            int availableForExtraction = extractHandler.extractEnergy(transferRate, true);
            if (availableForExtraction <= 0)
                continue;
            EnergyExtendedData.EnergySidedData sidedExtractData = extract.data.castTo(EnergyExtendedData.class).compute(extract.direction);

            if (inserts.size() <= sidedExtractData.rotatingIndex) {
                sidedExtractData.rotatingIndex = 0;
            }

            for (int j = sidedExtractData.rotatingIndex; j < sidedExtractData.rotatingIndex + inserts.size(); j++) {
                int insertIndex = j % inserts.size();
                CapabilityConnection insert = inserts.get(insertIndex);

                int inserted = insert.cap.receiveEnergy(availableForExtraction, false);
                extractHandler.extractEnergy(inserted, false);

                if (inserted == availableForExtraction) {
                    sidedExtractData.rotatingIndex += (insertIndex) + 1;
                    continue toNextExtract;
                }

                availableForExtraction -= inserted;
            }
        }
    }

    @Override
    public int getTickRate() {
        return this.tickRate;
    }
}
