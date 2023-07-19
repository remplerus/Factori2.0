package com.rempler.factori20.common.menu;

import com.rempler.factori20.common.abstractions.bases.BaseResearchContainerMenu;
import com.rempler.factori20.common.blockentity.BurnerResearchBlockEntity;
import com.rempler.factori20.common.blockentity.ElectricResearchBlockEntity;
import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.common.init.F20Menus;
import com.rempler.factori20.common.item.DrillHeadItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ElectricResearchMenu extends BaseResearchContainerMenu {
    public final ElectricResearchBlockEntity ebe;
    private final Level level;
    private final ContainerData data;
    public ElectricResearchMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, (ElectricResearchBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3), new ItemStackHandler(1));
    }

    public ElectricResearchMenu(int id, Inventory playerInventory, ElectricResearchBlockEntity drillBlockEntity, ContainerData data, ItemStackHandler outputHandler) {
        super(F20Menus.ELECTRIC_RESEARCH_CONTAINER.get(), id, playerInventory);
        checkContainerSize(playerInventory, 3);
        ebe = drillBlockEntity;
        this.level = playerInventory.player.level();
        this.data = data;

        addSlot(new SlotItemHandler(outputHandler, 0, 135, 35));

        this.ebe.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); ++i) {
                addSlot(new SlotItemHandler(handler, i, 62 + i * 18, 17 + 18));
            }
        });
    }

    public static MenuProvider getServerMenu(ElectricResearchBlockEntity be) {
        return new SimpleMenuProvider((id, playerInventory, serverPlayer) -> new ElectricResearchMenu(id, playerInventory, be, be.data, be.outputHandler),
                Component.literal(""));
    }

    public ElectricResearchBlockEntity getBlockEntity() {
        return this.ebe;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, ebe.getBlockPos()),
                pPlayer, F20Blocks.ELECTRIC_RESEARCH_BLOCK.get());
    }
}
