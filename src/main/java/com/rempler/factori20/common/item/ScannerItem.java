package com.rempler.factori20.common.item;

import com.rempler.factori20.api.energy.AbstractEnergyItem;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScannerItem extends AbstractEnergyItem {
    public ScannerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.getItem() instanceof ScannerItem scanner && stack.getCapability(ForgeCapabilities.ENERGY).isPresent()) {
            stack.getCapability(ForgeCapabilities.ENERGY).ifPresent(cap -> {
                if (!(cap.getEnergyStored() < scanner.getEnergyCost(stack))) {
                    if (stack.getDamageValue() == 0 && canUse(stack, pPlayer)) {
                        if (!pLevel.isClientSide) {
                            stack.setDamageValue(99);
                            this.applyDamage(stack, (ServerPlayer) pPlayer);
                            pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 0.1f);
                        }
                    } else {
                        pPlayer.displayClientMessage(Component.translatable("txt.f20.scanner.on_cooldown", (stack.getDamageValue() / 20) + 1), true);
                    }
                }});
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pStack.getDamageValue() > 0) {
            pStack.setDamageValue(pStack.getDamageValue() - 1);
        }
    }

    @Override
    public int getEnergyMax() {
        return F20Config.scannerMaxEnergy.get();
    }

    @Override
    public int getEnergyCost(ItemStack tool) {
        return F20Config.scannerEnergyCost.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        addEnergyInformation(pTooltipComponents, pStack);
    }
}
