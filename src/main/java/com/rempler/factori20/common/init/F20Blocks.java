package com.rempler.factori20.common.init;

import com.rempler.factori20.common.block.AbstractDrillBlock;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, F20Constants.MODID);
    public static final RegistryObject<AbstractDrillBlock> DRILL_BLOCK = BLOCKS.register("drill_block",
            () -> new AbstractDrillBlock(Block.Properties.of(Material.METAL).strength(3.0F, 3.0F).sound(SoundType.METAL)));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
