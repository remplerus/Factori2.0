package com.rempler.factori20.api.common.bases;

import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.api.chunk.ChunkResourceGenerator;
import com.rempler.factori20.api.common.AbstractF20BlockEntity;
import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class BaseDrillBlockEntity extends AbstractF20BlockEntity {
    private int drillSpeed = 0;
    private int drillTime = 0;

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("f20.container.drill");
    }

    public BaseDrillBlockEntity(BlockEntityType<?> entityType, BlockPos pPos, BlockState pBlockState) {
        super(entityType, pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BaseDrillBlockEntity.this.drillTime;
                    case 1 -> BaseDrillBlockEntity.this.drillSpeed;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BaseDrillBlockEntity.this.drillTime = pValue;
                    case 1 -> BaseDrillBlockEntity.this.drillSpeed = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        this.itemHandler = new ItemStackHandler(10) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot != 0 || stack.getItem() instanceof DrillHeadItem;
            }
        };
    }

    public static int getDrillSpeed(BaseDrillBlockEntity dbe) {
        return dbe.drillSpeed;
    }

    protected void updateDrillSpeed() {
        ItemStack drillItemStack = itemHandler.getStackInSlot(0);
        if (drillItemStack.getItem() instanceof DrillHeadItem) {
            drillSpeed = ((DrillHeadItem) drillItemStack.getItem()).getSpeed();
        } else {
            drillSpeed = 0;
        }
    }

    protected static boolean performDrillingActions(Level level, BlockPos worldPosition, BaseDrillBlockEntity blockEntity) {
        ItemStack drillStack = blockEntity.itemHandler.getStackInSlot(0);
        if (drillStack.isEmpty() || !(drillStack.getItem() instanceof DrillHeadItem)) {
            return false;
        }

        if (blockEntity.drillTime <= 0) {
            ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(new ChunkPos(worldPosition));
            if (resourceData != null) {
                String randomResource = resourceData.getRandomResource(level.random);
                if (randomResource != null) {
                    resourceData.removeResource(randomResource, 1);

                    ItemStack resourceStack = new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(new ResourceLocation(randomResource))));
                    boolean placedResource = false;
                    for (int slot = 1; slot <= 9; slot++) {
                        ItemStack stackInSlot = blockEntity.itemHandler.getStackInSlot(slot);
                        if (stackInSlot.isEmpty()) {
                            blockEntity.itemHandler.insertItem(slot, resourceStack, false);
                            placedResource = true;
                            break;
                        } else if (stackInSlot.getItem() == resourceStack.getItem() && (!ItemHandlerHelper.canItemStacksStack(stackInSlot, resourceStack) || stackInSlot.getCount() < 64)) {
                            stackInSlot.grow(1);
                            placedResource = true;
                            break;
                        }
                    }

                    if (placedResource) {
                        blockEntity.drillTime = blockEntity.drillSpeed;
                    } else {
                        blockEntity.drillTime = 0;
                    }
                }
                return true;
            }
        } else {
            blockEntity.drillTime--;
            return true;
        }
        return false;
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("inventory")) {
            itemHandler.deserializeNBT(pTag.getCompound("drill"));
        }

        drillTime = pTag.getInt("drillTime");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());

        pTag.putInt("drillTime", drillTime);
    }


    public static void tickServer(Level level, BlockPos pos, BlockState state, BaseDrillBlockEntity blockEntity) {
        if (level.isClientSide) return;
        if (blockEntity.drillSpeed == 0 || blockEntity.itemHandler.getStackInSlot(0).isEmpty()) {
            blockEntity.updateDrillSpeed();
            blockEntity.drillTime = blockEntity.drillSpeed;
        }
        if (shouldDrill(level, pos, blockEntity) && canInsert(blockEntity)) {
            if (performDrillingActions(level, pos, blockEntity)) {
                setChanged(level, pos, state);
            }
        }
    }

    protected static boolean shouldDrill(Level level, BlockPos worldPosition, BaseDrillBlockEntity blockEntity) {
        boolean truege = false;
        ChunkPos chunkPos = new ChunkPos(worldPosition);
        ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(level.getChunkAt(chunkPos.getWorldPosition()).getPos());

        if (resourceData != null && !(blockEntity.itemHandler.getStackInSlot(0).isEmpty())) {
            for(String resourceType : resourceData.serializeNBT().getAllKeys()) {
                if (resourceData.getResourceAmount(resourceType) > 0) {
                    truege = true;
                }
            }
        }
        return truege;
    }

    protected void resetProgress() {
        this.drillTime = 0;
    }
}
