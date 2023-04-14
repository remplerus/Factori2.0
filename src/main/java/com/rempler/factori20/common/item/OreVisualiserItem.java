package com.rempler.factori20.common.item;

import com.rempler.factori20.common.menu.VisualizerContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class OreVisualiserItem extends Item implements MenuProvider{
    public OreVisualiserItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            // open the VisualizerScreen GUI
            NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider) this);
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("item.factori20.visualizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VisualizerContainerMenu(pContainerId, pPlayerInventory);
    }
}
