package com.rempler.factori20.common.block;

import com.rempler.factori20.api.helpers.ExceptionHelper;
import com.rempler.factori20.common.abstractions.AbstractDrillBlock;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.init.F20BEs;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ElectricDrillBlock extends AbstractDrillBlock {
    public ElectricDrillBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity eb = pLevel.getBlockEntity(pPos);
            if (eb instanceof ElectricDrillBlockEntity dbe) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, dbe, pPos);
            } else {
                new ExceptionHelper().illegalException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ElectricDrillBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, F20BEs.ELECTRIC_DRILL.get(), ElectricDrillBlockEntity::tick);
    }
}
