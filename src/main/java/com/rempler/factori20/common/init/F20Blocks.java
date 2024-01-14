package com.rempler.factori20.common.init;

import com.rempler.factori20.common.block.BurnerDrillBlock;
import com.rempler.factori20.common.block.BurnerResearchBlock;
import com.rempler.factori20.common.block.ElectricDrillBlock;
import com.rempler.factori20.common.block.ElectricResearchBlock;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class F20Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, F20Constants.MODID);
    public static final DeferredHolder<Block, ElectricDrillBlock> ELECTRIC_DRILL_BLOCK = BLOCKS.register("electric_drill_block",
            () -> new ElectricDrillBlock(Block.Properties.of().strength(3.0F, 3.0F).sound(SoundType.METAL)));
    public static final DeferredHolder<Block, BurnerDrillBlock> BURNER_DRILL_BLOCK = BLOCKS.register("burner_drill_block",
            () -> new BurnerDrillBlock(Block.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, ElectricResearchBlock> ELECTRIC_RESEARCH_BLOCK = BLOCKS.register("electric_research_block",
            () -> new ElectricResearchBlock(Block.Properties.of().strength(3.0F, 3.0F).sound(SoundType.METAL)));
    public static final DeferredHolder<Block, BurnerResearchBlock> BURNER_RESEARCH_BLOCK = BLOCKS.register("burner_research_block",
            () -> new BurnerResearchBlock(Block.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
