package com.rempler.factori20.api.energy;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
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
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        ImmutableList.Builder<ICapabilityProvider> providerBuilder = ImmutableList.builder();
        providerBuilder.add(new EnergyCapabilityProvider(stack, this::getEnergyMax));
        return providerBuilder.build().get(0);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        LazyOptional<IEnergyStorage> cap = pStack.getCapability(ForgeCapabilities.ENERGY);
        if (!cap.isPresent()) {return super.getBarWidth(pStack);}
        return cap.map(e -> Math.min(13 * e.getEnergyStored() / e.getMaxEnergyStored(), 13)).orElse(super.getBarWidth(pStack));
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        LazyOptional<IEnergyStorage> cap = pStack.getCapability(ForgeCapabilities.ENERGY);
        if (!cap.isPresent()) {return super.getBarColor(pStack);}

        Pair<Integer, Integer> energyStorage = cap.map(e -> Pair.of(e.getEnergyStored(), e.getMaxEnergyStored())).orElse(Pair.of(0,0));
        return Mth.hsvToRgb(Math.max(0.0f, energyStorage.getLeft() / (float) energyStorage.getRight()) / 3.0F, 1F, 1F);
    }

    @Override
    public boolean isDamaged(ItemStack pStack) {
        LazyOptional<IEnergyStorage> cap = pStack.getCapability(ForgeCapabilities.ENERGY);
        if (!cap.isPresent()) {return super.isDamaged(pStack);}

        Pair<Integer, Integer> energyStorage = cap.map(e -> Pair.of(e.getEnergyStored(), e.getMaxEnergyStored())).orElse(Pair.of(0,0));
        return !energyStorage.getLeft().equals(energyStorage.getRight());
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return pStack.getCapability(ForgeCapabilities.ENERGY).map(e -> e.getEnergyStored() != e.getMaxEnergyStored()).orElse(super.isBarVisible(pStack));
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return !pStack.getCapability(ForgeCapabilities.ENERGY).isPresent();
    }

    public boolean canUse(ItemStack item, Player player) {
        if (player.isCreative() || getEnergyMax() == 0) {return true;}

        return getEnergyCost(item) <= item.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public void applyDamage(ItemStack item, ServerPlayer player) {
        if (player.isCreative() || getEnergyMax() == 0) {return;}

        item.getCapability(ForgeCapabilities.ENERGY).ifPresent(e -> ((IF20Energy) e).extractEnergyPower(getEnergyCost(item), false));
    }

    protected void addEnergyInformation(List<Component> tooltip, ItemStack item) {
        if (getEnergyMax() == 0) {return;}

        item.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy ->
                tooltip.add(Component.translatable("tooltip.f20.energyStored", energy.getEnergyStored(),
                        energy.getMaxEnergyStored()).withStyle(ChatFormatting.GRAY)));
    }
}
