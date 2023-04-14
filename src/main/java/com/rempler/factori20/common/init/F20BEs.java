package com.rempler.factori20.common.init;

import com.rempler.factori20.common.blockentity.DrillBlockEntity;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20BEs {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, F20Constants.MODID);
    public static final RegistryObject<BlockEntityType<DrillBlockEntity>> DRILL = BLOCK_ENTITIES.register("drill_block_entity",
            () -> BlockEntityType.Builder.of(DrillBlockEntity::new, F20Blocks.DRILL_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
