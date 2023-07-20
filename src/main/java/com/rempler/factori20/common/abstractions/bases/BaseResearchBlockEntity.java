package com.rempler.factori20.common.abstractions.bases;

import com.rempler.factori20.common.abstractions.AbstractF20BlockEntity;
import com.rempler.factori20.common.init.F20Recipes;
import com.rempler.factori20.common.item.ResearchItem;
import com.rempler.factori20.common.recipe.ResearchRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class BaseResearchBlockEntity extends AbstractF20BlockEntity {
    public ItemStackHandler outputHandler;
    protected int progress;
    protected int maxProgress;
    public LazyOptional<IItemHandler> lazyOutputHandler = LazyOptional.empty();
    @Override
    public Component getDisplayName() {
        return Component.translatable("f20.container.researcher");
    }

    public BaseResearchBlockEntity(BlockEntityType<?> entityType, BlockPos pPos, BlockState pBlockState) {
        super(entityType, pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BaseResearchBlockEntity.this.progress;
                    case 1 -> BaseResearchBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BaseResearchBlockEntity.this.progress = pValue;
                    case 1 -> BaseResearchBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        this.outputHandler = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof ResearchItem;
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
    public void onLoad() {
        super.onLoad();
        lazyOutputHandler = LazyOptional.of(() -> outputHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyOutputHandler.invalidate();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("inventory")) {
            itemHandler.deserializeNBT(pTag.getCompound("inventory"));
            outputHandler.deserializeNBT(pTag.getCompound("output"));
        }
        if (pTag.contains("progress")) {
            progress = pTag.getInt("progress");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("output", outputHandler.serializeNBT());
        pTag.putInt("progress", progress);
    }

    protected boolean isRecipeThere(Level level, SimpleContainer inventory) {
        Optional<ResearchRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ResearchRecipe.Type.INSTANCE, inventory, level);
        return recipe.isPresent();
    }

    protected static void tickServer(Level level, BaseResearchBlockEntity blockEntity, SimpleContainer inventory) {
        if (level != null) {
            if (level.isClientSide) {
                return;
            }
            Optional<ResearchRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(F20Recipes.RESEARCH_RECIPE.get(), inventory, level);
            SimpleContainer outputInventory = new SimpleContainer(1);
            outputInventory.addItem(blockEntity.outputHandler.getStackInSlot(0));
            if (recipe.isPresent() && hasRecipe(level, outputInventory, blockEntity.outputHandler.getSlots(), recipe.get())) {
                if (blockEntity.maxProgress <= 0) {
                    blockEntity.maxProgress = recipe.get().getResearchTime();
                }
                if (blockEntity.progress >= blockEntity.maxProgress) {
                    doRecipe(level, inventory, blockEntity, recipe.get());
                    resetProgress(blockEntity);
                }
                if (blockEntity.progress < blockEntity.maxProgress) {
                    blockEntity.progress++;
                }
            }
        }
    }

    protected static void resetProgress(BaseResearchBlockEntity blockEntity) {
        blockEntity.progress = 0;
        blockEntity.maxProgress = 0;
    }

    protected static void doRecipe(Level level, SimpleContainer inventory, BaseResearchBlockEntity blockEntity, ResearchRecipe recipe) {
        for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }
        SimpleContainer outputInventory = new SimpleContainer(1);
        outputInventory.addItem(blockEntity.outputHandler.getStackInSlot(0));
        if (hasRecipe(level, outputInventory, blockEntity.outputHandler.getSlots(), recipe)) {
            for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
                blockEntity.itemHandler.extractItem(i, 1, false);
            }
            blockEntity.outputHandler.insertItem(0, recipe.getResultItem(level.registryAccess()), false);
        }
    }

    private static boolean hasRecipe(Level level, SimpleContainer inventory, int slots, ResearchRecipe recipe) {
        boolean truege = false;
        for (int i = 0; i < slots; i++) {
            if (canInsertAmountIntoOutputSlot(inventory,i) &&
                    canInsertItemIntoOutputSlot(inventory, recipe.getResultItem(level.registryAccess()), i)) {
                truege = true;
            }
        }
        return truege;
    }
}
