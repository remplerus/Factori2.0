package com.rempler.factori20.compat.eio;

import com.enderio.EnderIO;
import com.enderio.api.conduit.ConduitTypes;
import com.enderio.api.conduit.IConduitType;
import com.enderio.api.misc.Vector2i;
import com.rempler.factori20.compat.eio.customapi.CustomEnergyConduitType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class CustomEnderConduitTypes {
    public static final ResourceLocation ICON_TEXTURE = EnderIO.loc("textures/gui/conduit_icon.png");
    public static final RegistryObject<? extends IConduitType<?>> SLOW_ENERGY = energyConduit("slow_energy", 20, new Vector2i(0, 24));
    public static final RegistryObject<? extends IConduitType<?>> NEW_ENERGY = energyConduit("new_energy", 50, new Vector2i(0, 36));
    public static final RegistryObject<? extends IConduitType<?>> FAST_ENERGY = energyConduit("fast_energy", 100, new Vector2i(0, 48));

    private static RegistryObject<CustomEnergyConduitType> energyConduit(String name, int tier, Vector2i iconPos) {
        return ConduitTypes.CONDUIT_TYPES.register(name+"_conduit",
                () -> new CustomEnergyConduitType(EnderIO.loc("block/conduit/"+name), tier, ICON_TEXTURE, iconPos));
    }
}
