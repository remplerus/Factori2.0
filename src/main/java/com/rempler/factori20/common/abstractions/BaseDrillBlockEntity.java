package com.rempler.factori20.common.abstractions;

import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.api.chunk.ChunkResourceGenerator;
import com.rempler.factori20.api.chunk.ResourceType;
import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseDrillBlockEntity extends RandomizableContainerBlockEntity {
    public static final int SIZE = 9;
    private int drillTime = 0;
    protected final ItemStackHandler outputHandler = new ItemStackHandler(SIZE) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            ItemStack existingStack = getStackInSlot(slot);

            if (!existingStack.isEmpty() && (!ItemHandlerHelper.canItemStacksStack(stack, existingStack) || existingStack.getCount() >= 64)) {
                return stack;
            }

            return super.insertItem(slot, stack, simulate);
        }
    };
    protected final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private int drillSpeed = 0;
    public BaseDrillBlockEntity(BlockEntityType<?> entityType, BlockPos pPos, BlockState pBlockState) {
        super(entityType, pPos, pBlockState);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> nnl = NonNullList.create();
        for (int i = 0; i < SIZE; i++) {
            nnl.add(outputHandler.getStackInSlot(i));
        }
        return nnl;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        for (int i = 0; i < SIZE; i++) {
            outputHandler.setStackInSlot(i, pItemStacks.get(i));
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("f20.container.drill");
    }

    @Override
    public int getContainerSize() {
        return SIZE;
    }

    protected void updateDrillSpeed() {
        ItemStack drillItemStack = inputHandler.getStackInSlot(0);
        if (drillItemStack.getItem() instanceof DrillHeadItem) {
            this.drillSpeed = ((DrillHeadItem) drillItemStack.getItem()).getSpeed();
        } else {
            this.drillSpeed = 0;
        }
    }

    protected void performDrillingActions() {
        ItemStack drillStack = inputHandler.getStackInSlot(0);
        if (drillStack.isEmpty() || !(drillStack.getItem() instanceof DrillHeadItem)) {
            return;
        }

        if (drillTime <= 0) {
            ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(new ChunkPos(worldPosition));
            if (resourceData != null) {
                ResourceType randomResource = resourceData.getRandomResource(level.random);
                if (randomResource != null) {
                    resourceData.removeResource(randomResource, 1);

                    ItemStack resourceStack = new ItemStack(randomResource.getItem());
                    boolean placedResource = false;
                    for (int slot = 0; slot <= 8; slot++) {
                        ItemStack stackInSlot = outputHandler.getStackInSlot(slot);
                        if (stackInSlot.isEmpty()) {
                            outputHandler.setStackInSlot(slot, resourceStack);
                            placedResource = true;
                            break;
                        } else if (stackInSlot.getItem() == resourceStack.getItem() && (!ItemHandlerHelper.canItemStacksStack(stackInSlot, resourceStack) || stackInSlot.getCount() < 64)) {
                            stackInSlot.grow(1);
                            placedResource = true;
                            break;
                        }
                    }

                    if (placedResource) {
                        drillTime = drillSpeed;
                    } else {
                        drillTime = drillSpeed;
                    }
                }
            }
        } else {
            drillTime--;
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("inventory")) {
            outputHandler.deserializeNBT(pTag.getCompound("inventory"));
        }

        if (pTag.contains("input")) {
            inputHandler.deserializeNBT(pTag.getCompound("input"));
        }

        drillTime = pTag.getInt("drillTime");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", outputHandler.serializeNBT());
        pTag.put("input", inputHandler.serializeNBT());
        pTag.putInt("drillTime", drillTime);
    }

    public void tickServer() {
        if (level != null && !level.isClientSide) {
            updateDrillSpeed();
        }
    }

    protected boolean shouldDrill() {
        ChunkPos chunkPos = new ChunkPos(this.worldPosition);
        ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(level.getChunkAt(chunkPos.getWorldPosition()).getPos());

        if (resourceData != null) {
            for(ResourceType resourceType : ResourceType.values()) {
                if (resourceData.getResourceAmount(resourceType) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
