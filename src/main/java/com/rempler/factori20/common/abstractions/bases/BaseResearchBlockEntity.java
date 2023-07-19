package com.rempler.factori20.common.abstractions.bases;

import com.rempler.factori20.common.abstractions.AbstractF20BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public abstract class BaseResearchBlockEntity extends AbstractF20BlockEntity {
    public ItemStackHandler outputHandler;
    public LazyOptional<IItemHandler> lazyOutputHandler = LazyOptional.empty();
    @Override
    public Component getDisplayName() {
        return Component.translatable("f20.container.researcher");
    }

    public BaseResearchBlockEntity(BlockEntityType<?> entityType, BlockPos pPos, BlockState pBlockState) {
        super(entityType, pPos, pBlockState);
        this.data = new SimpleContainerData(1);
        this.outputHandler = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return false;
            }
        };
        this.itemHandler = new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("inventory")) {
            itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, BaseResearchBlockEntity blockEntity) {
        if (level.isClientSide) return;
    }
}
