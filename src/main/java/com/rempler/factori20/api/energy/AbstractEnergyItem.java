package com.rempler.factori20.api.energy;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractEnergyItem extends Item {
    public AbstractEnergyItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract int getEnergyMax();
    public abstract int getEnergyCost(ItemStack tool);

    @Override
    public int getBarWidth(ItemStack pStack) {
        @Nullable IEnergyStorage cap = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        assert cap != null;

        return Math.min(13 * cap.getEnergyStored() / cap.getMaxEnergyStored(), 13);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        @Nullable IEnergyStorage cap = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        assert cap != null;

        Pair<Integer, Integer> energyStorage = Pair.of(cap.getEnergyStored(), cap.getMaxEnergyStored());
        return Mth.hsvToRgb(Math.max(0.0f, energyStorage.getLeft() / (float) energyStorage.getRight()) / 3.0F, 1F, 1F);
    }

    @Override
    public boolean isDamaged(ItemStack pStack) {
        @Nullable IEnergyStorage cap = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        assert cap != null;

        Pair<Integer, Integer> energyStorage = Pair.of(cap.getEnergyStored(), cap.getMaxEnergyStored());
        return !energyStorage.getLeft().equals(energyStorage.getRight());
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        @Nullable IEnergyStorage cap = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null) {return false;}
        return cap.getEnergyStored() != cap.getMaxEnergyStored();
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        @Nullable IEnergyStorage cap = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null) {return false;}
        return !cap.canExtract() || !cap.canReceive();
    }

    public boolean canUse(ItemStack item, Player player) {
        if (player.isCreative() || getEnergyMax() == 0) {return true;}
        @Nullable IEnergyStorage cap = item.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null) {return false;}
        return getEnergyCost(item) <= cap.getEnergyStored();
    }

    public void applyDamage(ItemStack item, ServerPlayer player) {
        if (player.isCreative() || getEnergyMax() == 0) {return;}
        @Nullable IEnergyStorage cap = item.getCapability(Capabilities.EnergyStorage.ITEM);

        if (cap != null) {
            ((IF20Energy) cap).extractEnergyPower(getEnergyCost(item), false);
        }
    }

    protected void addEnergyInformation(List<Component> tooltip, ItemStack item) {
        if (getEnergyMax() == 0) {return;}
        @Nullable IEnergyStorage cap = item.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap != null) {
            tooltip.add(Component.translatable("tooltip.f20.energyStored", cap.getEnergyStored(),
                    cap.getMaxEnergyStored()).withStyle(ChatFormatting.GRAY));
        }
    }
}
