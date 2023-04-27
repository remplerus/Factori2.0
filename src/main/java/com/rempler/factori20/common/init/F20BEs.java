package com.rempler.factori20.common.init;

import com.rempler.factori20.common.blockentity.BurnerDrillBlockEntity;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20BEs {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, F20Constants.MODID);
    public static final RegistryObject<BlockEntityType<ElectricDrillBlockEntity>> ELECTRIC_DRILL = BLOCK_ENTITIES.register("electric_drill_block_entity",
            () -> BlockEntityType.Builder.of(ElectricDrillBlockEntity::new, F20Blocks.ELECTRIC_DRILL_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BurnerDrillBlockEntity>> BURNER_DRILL = BLOCK_ENTITIES.register("burner_drill_block_entity",
            () -> BlockEntityType.Builder.of(BurnerDrillBlockEntity::new, F20Blocks.BURNER_DRILL_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
