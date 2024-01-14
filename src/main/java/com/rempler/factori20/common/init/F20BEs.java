package com.rempler.factori20.common.init;

import com.rempler.factori20.common.blockentity.BurnerDrillBlockEntity;
import com.rempler.factori20.common.blockentity.BurnerResearchBlockEntity;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.blockentity.ElectricResearchBlockEntity;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class F20BEs {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, F20Constants.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricDrillBlockEntity>> ELECTRIC_DRILL = BLOCK_ENTITIES.register("electric_drill_block_entity",
            () -> BlockEntityType.Builder.of(ElectricDrillBlockEntity::new, F20Blocks.ELECTRIC_DRILL_BLOCK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BurnerDrillBlockEntity>> BURNER_DRILL = BLOCK_ENTITIES.register("burner_drill_block_entity",
            () -> BlockEntityType.Builder.of(BurnerDrillBlockEntity::new, F20Blocks.BURNER_DRILL_BLOCK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricResearchBlockEntity>> ELECTRIC_RESEARCH = BLOCK_ENTITIES.register("electric_research_block_entity",
            () -> BlockEntityType.Builder.of(ElectricResearchBlockEntity::new, F20Blocks.ELECTRIC_RESEARCH_BLOCK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BurnerResearchBlockEntity>> BURNER_RESEARCH = BLOCK_ENTITIES.register("burner_research_block_entity",
            () -> BlockEntityType.Builder.of(BurnerResearchBlockEntity::new, F20Blocks.BURNER_RESEARCH_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
