package com.rempler.factori20.common.abstractions;

import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.api.chunk.ChunkResourceGenerator;
import com.rempler.factori20.api.chunk.ResourceType;
import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseDrillBlockEntity extends BlockEntity implements MenuProvider {

    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1, 2, 3, 4, 5, 6, 7, 8 -> false;
                case 9 -> stack.getItem() instanceof DrillHeadItem;
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    protected final ContainerData data;
    private int drillSpeed = 0;
    private int drillTime = 0;

    @Override
    public Component getDisplayName() {
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
    }

    public static int getDrillSpeed(BaseDrillBlockEntity dbe) {
        return dbe.drillSpeed;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }

    protected void updateDrillSpeed() {
        ItemStack drillItemStack = itemHandler.getStackInSlot(9);
        if (drillItemStack.getItem() instanceof DrillHeadItem) {
            drillSpeed = ((DrillHeadItem) drillItemStack.getItem()).getSpeed();
        } else {
            drillSpeed = 0;
        }
    }

    protected static boolean performDrillingActions(Level level, BlockPos worldPosition, BaseDrillBlockEntity blockEntity) {
        ItemStack drillStack = blockEntity.itemHandler.getStackInSlot(9);
        if (drillStack.isEmpty() || !(drillStack.getItem() instanceof DrillHeadItem)) {
            return false;
        }

        if (blockEntity.drillTime <= 0) {
            ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(new ChunkPos(worldPosition));
            if (resourceData != null) {
                ResourceType randomResource = resourceData.getRandomResource(level.random);
                if (randomResource != null) {
                    resourceData.removeResource(randomResource, 1);

                    ItemStack resourceStack = new ItemStack(randomResource.getItem());
                    boolean placedResource = false;
                    for (int slot = 0; slot <= 8; slot++) {
                        ItemStack stackInSlot = blockEntity.itemHandler.getStackInSlot(slot);
                        if (stackInSlot.isEmpty()) {
                            blockEntity.itemHandler.setStackInSlot(slot, resourceStack);
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
                        blockEntity.drillTime = blockEntity.drillSpeed;
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
            itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        }

        drillTime = pTag.getInt("drillTime");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("drillTime", drillTime);
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, BaseDrillBlockEntity blockEntity) {
        if (level.isClientSide) return;
        if (blockEntity.drillSpeed == 0 || blockEntity.itemHandler.getStackInSlot(9).isEmpty()) {
            blockEntity.updateDrillSpeed();
            blockEntity.drillTime = blockEntity.drillSpeed;
        }
        if (shouldDrill(level, pos) && canInsert(blockEntity)) {
            if (performDrillingActions(level, pos, blockEntity)) {
                setChanged(level, pos, state);
            }
        }
    }

    protected static boolean shouldDrill(Level level, BlockPos worldPosition) {
        ChunkPos chunkPos = new ChunkPos(worldPosition);
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

    protected static boolean canInsert(BaseDrillBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots() - 1);
        boolean truege = true;

        for (int i = 0; i < blockEntity.itemHandler.getSlots() - 1; i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
            if (!canInsertAmountIntoOutputSlot(inventory, i) || !canInsertItemIntoOutputSlot(inventory, blockEntity.itemHandler.getStackInSlot(i), i)) {
                truege = false;
            }
            if (canInsertAmountIntoOutputSlot(inventory, i) && canInsertItemIntoOutputSlot(inventory, blockEntity.itemHandler.getStackInSlot(i), i)) {
                truege = true;
                break;
            }
        }

        return truege;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack, int i) {
        return inventory.getItem(i).getItem() == stack.getItem() || inventory.getItem(i).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int i) {
        return inventory.getItem(i).getMaxStackSize() > inventory.getItem(i).getCount();
    }

    protected void resetProgress() {
        this.drillTime = 0;
    }
}
