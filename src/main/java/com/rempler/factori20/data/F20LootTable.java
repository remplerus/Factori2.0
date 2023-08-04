package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Blocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class F20LootTable extends BlockLootSubProvider {

    public F20LootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (RegistryObject<Block> block : F20Blocks.BLOCKS.getEntries()) {
            dropSelf(block.get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return F20Blocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public static class F20LootTableProvider {
        public static LootTableProvider create(PackOutput output) {
            return new LootTableProvider(output, Set.of(),
                    List.of(new LootTableProvider.SubProviderEntry(F20LootTable::new, LootContextParamSets.BLOCK)));
        }
    }
}
