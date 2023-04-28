package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.BaseDrillContainerMenu;
import com.rempler.factori20.common.blockentity.BurnerDrillBlockEntity;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BurnerDrillContainerMenu extends BaseDrillContainerMenu {
    BurnerDrillBlockEntity dbe;
    public BurnerDrillContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (BurnerDrillBlockEntity) playerInventory.player.level.getBlockEntity(buf.readBlockPos()), new ItemStackHandler(1), new ItemStackHandler(9));
    }

    public BurnerDrillContainerMenu(int id, Inventory playerInventory, BurnerDrillBlockEntity drillBlockEntity, ItemStackHandler inputHandler, ItemStackHandler outputHandler) {
        super(F20Menus.BURNER_DRILL_CONTAINER.get(), id, playerInventory, inputHandler, outputHandler);
        this.dbe = drillBlockEntity;

        addSlot(new SlotItemHandler(BurnerDrillBlockEntity.fuelHandler, 0, 25, 45));
    }

    public int getFlameScaled() {
        int burnTime = dbe.getBurnTime();
        int maxBurnTime = dbe.getMaxBurnTime();
        if (burnTime <= 0 || maxBurnTime <= 0) {
            return 0;
        }
        return (int) (14*((float) burnTime / maxBurnTime));
    }
}
