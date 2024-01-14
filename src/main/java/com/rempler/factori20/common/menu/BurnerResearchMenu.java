package com.rempler.factori20.common.menu;

import com.rempler.factori20.api.common.bases.BaseResearchContainerMenu;
import com.rempler.factori20.common.blockentity.BurnerResearchBlockEntity;
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
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BurnerResearchMenu extends BaseResearchContainerMenu {

    public BurnerResearchMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (BurnerResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1), new ItemStackHandler(1));
    }

    public BurnerResearchMenu(int id, Inventory playerInventory, BurnerResearchBlockEntity researchBlockEntity, ContainerData data, ItemStackHandler fuelHandler, ItemStackHandler outputHandler) {
        super(F20Menus.BURNER_RESEARCH_CONTAINER.get(), id, playerInventory, researchBlockEntity, outputHandler, data);
        checkContainerSize(playerInventory, 3);

        addSlot(new SlotItemHandler(fuelHandler, 0, 25, 45));

        addDataSlots(((BurnerResearchBlockEntity)ebe).burnData);
    }

    public static MenuProvider getServerMenu(BurnerResearchBlockEntity be) {
        return new SimpleMenuProvider((id, playerInventory, serverPlayer) -> new BurnerResearchMenu(id, playerInventory, be, be.burnData, be.fuelHandler, be.outputHandler),
                Component.literal(""));
    }

    public static BurnerResearchMenu getClientMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        return new BurnerResearchMenu(id, playerInventory, (BurnerResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1), new ItemStackHandler(1));
    }

    public int getFlameScaled() {
        int totalTime = ((BurnerResearchBlockEntity)ebe).burnData.get(1);
        int actualTime = ((BurnerResearchBlockEntity)ebe).burnData.get(0);

        return totalTime != 0 && actualTime != 0 ? actualTime*13/totalTime : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.BURNER_RESEARCH_BLOCK.get());
    }

}
