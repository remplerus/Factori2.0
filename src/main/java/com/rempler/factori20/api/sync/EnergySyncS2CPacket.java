package com.rempler.factori20.api.sync;

import com.rempler.factori20.common.blockentity.ElectricDrillBlockEntity;
import com.rempler.factori20.common.menu.ElectricDrillMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ElectricDrillBlockEntity edbe) {
                edbe.setEnergyLevel(energy);

                if (Minecraft.getInstance().player.containerMenu instanceof ElectricDrillMenu menu &&
                    menu.getBlockEntity().getBlockPos().equals(pos)) {
                    edbe.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}
