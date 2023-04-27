package com.rempler.factori20.common.abstractions;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;

public abstract class AbstractDrillBlock extends Block implements EntityBlock {
    public AbstractDrillBlock(Properties pProperties) {
        super(pProperties);
    }
}
