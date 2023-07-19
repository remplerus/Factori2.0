package com.rempler.factori20.common.abstractions.bases;

import com.rempler.factori20.common.abstractions.AbstractF20BaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseResearchBlock extends AbstractF20BaseEntityBlock {
    public BaseResearchBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BaseResearchBlockEntity brbe) {
                brbe.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
