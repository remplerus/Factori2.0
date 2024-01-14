package com.rempler.factori20.common.item;

import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.api.chunk.ChunkResourceGenerator;
import com.rempler.factori20.api.energy.AbstractEnergyItem;
import com.rempler.factori20.api.helpers.WordHelper;
import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.utils.F20Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScannerItem extends AbstractEnergyItem {
    public ScannerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.getItem() instanceof ScannerItem scanner && !(pLevel.isClientSide)) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            IEnergyStorage cap = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (!(cap.getEnergyStored() < scanner.getEnergyCost(stack))) {
                if (stack.getDamageValue() == 0 && canUse(stack, serverPlayer)) {
                    BlockPos pos = serverPlayer.blockPosition();
                    // Berechne den Chunk, in dem sich der Spieler befindet
                    int chunkX = pos.getX() >> 4;
                    int chunkZ = pos.getZ() >> 4;

                    // Holen Sie sich die ChunkResourceData für den aktuellen Chunk
                    ChunkResourceData resourceData = ChunkResourceGenerator.getChunkResourceData(new ChunkPos(chunkX, chunkZ));
                    boolean success = false;
                    boolean noOreFound = true;
                    if (!(resourceData == null)) {
                        serverPlayer.sendSystemMessage(Component.translatable("txt.f20.scan_results_header").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD), false);
                        for (String resourceType : resourceData.serializeNBT().getAllKeys()) {
                            int amount = resourceData.getResourceAmount(resourceType);
                            if (amount > 0) {
                                // Zeige die verfügbaren Erze an
                                serverPlayer.sendSystemMessage(Component.translatable("txt.f20.ores_available", WordHelper.capitalizeFully(resourceType.split(":")[1].replace("_", " ")), amount));
                                success = true;
                                noOreFound = false;
                            } else {
                                noOreFound = false;
                            }

                            stack.setDamageValue(99);
                            this.applyDamage(stack, serverPlayer);
                        }
                        if (success) {
                            pLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 0.1f);
                        }
                        if (noOreFound) {
                            serverPlayer.sendSystemMessage(Component.translatable("txt.f20.no_ores_available"), true);
                        }
                    } else {
                        serverPlayer.sendSystemMessage(Component.literal("literally no data :("));
                    }
                } else {
                    serverPlayer.sendSystemMessage(Component.translatable("txt.f20.scanner.on_cooldown", (stack.getDamageValue() / 20) + 1), true);
                }
            } else if (serverPlayer.isCreative() && serverPlayer.isShiftKeyDown()) {
                cap.receiveEnergy(100000, false);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getItemInHand().is(F20Items.SCANNER.get()) && pContext.getPlayer() != null && !pContext.getLevel().isClientSide) {
            Player player = pContext.getPlayer();
            if (player.isCreative() && player.isShiftKeyDown()) {
                if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof ElectricDrillBlockEntity ebe) {
                    ebe.setEnergyLevel(1000000);
                }
            }
        }
        return InteractionResult.PASS;
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
