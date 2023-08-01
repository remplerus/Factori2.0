package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.bases.BaseResearchContainerMenu;
import com.rempler.factori20.common.blockentity.ElectricResearchBlockEntity;
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
import net.minecraftforge.items.ItemStackHandler;

public class ElectricResearchMenu extends BaseResearchContainerMenu {
    public ElectricResearchMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (ElectricResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1));
    }

    public ElectricResearchMenu(int id, Inventory playerInventory, ElectricResearchBlockEntity drillBlockEntity, ContainerData data, ItemStackHandler outputHandler) {
        super(F20Menus.ELECTRIC_RESEARCH_CONTAINER.get(), id, playerInventory, drillBlockEntity, outputHandler, data);
        checkContainerSize(playerInventory, 3);
    }

    public static MenuProvider getServerMenu(ElectricResearchBlockEntity be) {
        return new SimpleMenuProvider((id, playerInventory, serverPlayer) -> new ElectricResearchMenu(id, playerInventory, be, be.data, be.outputHandler),
                Component.literal(""));
    }

    public ElectricResearchBlockEntity getBlockEntity() {
        return (ElectricResearchBlockEntity) this.ebe;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.ELECTRIC_RESEARCH_BLOCK.get());
    }
}
