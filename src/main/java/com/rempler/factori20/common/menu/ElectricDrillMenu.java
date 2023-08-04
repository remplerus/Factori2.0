package com.rempler.factori20.common.menu;

import com.rempler.factori20.api.common.bases.BaseDrillContainerMenu;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.common.init.F20Menus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class ElectricDrillMenu extends BaseDrillContainerMenu {
    public final ElectricDrillBlockEntity ebe;
    private final Level level;
    private final ContainerData data;
    public ElectricDrillMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (ElectricDrillBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(2));
    }

    public ElectricDrillMenu(int id, Inventory playerInventory, ElectricDrillBlockEntity drillBlockEntity, ContainerData data) {
        super(F20Menus.ELECTRIC_DRILL_CONTAINER.get(), id, playerInventory, drillBlockEntity);
        checkContainerSize(playerInventory, 10);
        ebe = drillBlockEntity;
        this.level = playerInventory.player.level();
        this.data = data;
    }

    public ElectricDrillBlockEntity getBlockEntity() {
        return this.ebe;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.ELECTRIC_DRILL_BLOCK.get());
    }
}
