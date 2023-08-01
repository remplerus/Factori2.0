package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.bases.BaseDrillContainerMenu;
import com.rempler.factori20.common.blockentity.BurnerDrillBlockEntity;
import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BurnerDrillMenu extends BaseDrillContainerMenu {
    public final BurnerDrillBlockEntity dbe;
    private final Level level;
    private final ContainerData data;

    public BurnerDrillMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (BurnerDrillBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(2), new ItemStackHandler(1));
    }

    public BurnerDrillMenu(int id, Inventory playerInventory, BurnerDrillBlockEntity drillBlockEntity, ContainerData data, ItemStackHandler fuelHandler) {
        super(F20Menus.BURNER_DRILL_CONTAINER.get(), id, playerInventory, drillBlockEntity);
        checkContainerSize(playerInventory, 10);
        this.dbe = drillBlockEntity;
        this.level = playerInventory.player.level();
        this.data = data;

        addSlot(new SlotItemHandler(fuelHandler, 0, 25, 45));

        addDataSlots(data);
        addDataSlots(dbe.burnData);
    }

    public static MenuProvider getServerMenu(BurnerDrillBlockEntity be) {
        return new SimpleMenuProvider((id, playerInventory, serverPlayer) -> new BurnerDrillMenu(id, playerInventory, be, be.burnData, be.fuelHandler),
                Component.literal(""));
    }

    public static BurnerDrillMenu getClientMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        return new BurnerDrillMenu(id, playerInventory, (BurnerDrillBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(4), new ItemStackHandler(1));
    }

    public int getFlameScaled() {
        int totalTime = this.dbe.burnData.get(1);
        int actualTime = this.dbe.burnData.get(0);

        return totalTime != 0 && actualTime != 0 ? actualTime*13/totalTime : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.BURNER_DRILL_BLOCK.get());
    }

}
