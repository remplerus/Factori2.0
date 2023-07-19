package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.bases.BaseResearchContainerMenu;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BurnerResearchMenu extends BaseResearchContainerMenu {
    public final BurnerResearchBlockEntity ebe;
    private final Level level;
    private final ContainerData data;

    public BurnerResearchMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (BurnerResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1), new ItemStackHandler(1));
    }

    public BurnerResearchMenu(int id, Inventory playerInventory, BurnerResearchBlockEntity researchBlockEntity, ContainerData data, ItemStackHandler fuelHandler, ItemStackHandler outputHandler) {
        super(F20Menus.BURNER_RESEARCH_CONTAINER.get(), id, playerInventory);
        checkContainerSize(playerInventory, 3);
        this.ebe = researchBlockEntity;
        this.level = playerInventory.player.level();
        this.data = data;

        addSlot(new SlotItemHandler(fuelHandler, 0, 25, 45));
        addSlot(new SlotItemHandler(outputHandler, 0, 135, 35));

        this.ebe.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); ++i) {
                addSlot(new SlotItemHandler(handler, i, 62 + i * 18, 17 + 18));
            }
        });

        addDataSlots(data);
        addDataSlots(ebe.burnData);
    }

    public static MenuProvider getServerMenu(BurnerResearchBlockEntity be) {
        return new SimpleMenuProvider((id, playerInventory, serverPlayer) -> new BurnerResearchMenu(id, playerInventory, be, be.burnData, be.fuelHandler, be.outputHandler),
                Component.literal(""));
    }

    public static BurnerResearchMenu getClientMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        return new BurnerResearchMenu(id, playerInventory, (BurnerResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1), new ItemStackHandler(1));
    }

    public int getFlameScaled() {
        int totalTime = this.data.get(1);
        int actualTime = this.data.get(0);

        return totalTime != 0 && actualTime != 0 ? actualTime*13/totalTime : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.BURNER_RESEARCH_BLOCK.get());
    }

}
