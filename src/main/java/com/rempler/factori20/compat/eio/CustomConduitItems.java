package com.rempler.factori20.compat.eio;

import com.enderio.EnderIO;
import com.enderio.api.conduit.ConduitItemFactory;
import com.enderio.api.conduit.IConduitType;
import com.enderio.base.common.init.EIOCreativeTabs;
import com.rempler.factori20.utils.F20Constants;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class CustomConduitItems {
    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(F20Constants.MODID));

    private static final Registrate REGISTRATE_INSTANCE = registrate();
    public static final ItemEntry<Item> SLOW_ENERGY = createCustomConduitItem(CustomEnderConduitTypes.SLOW_ENERGY, "slow_energy");
    public static final ItemEntry<Item> NEW_ENERGY = createCustomConduitItem(CustomEnderConduitTypes.NEW_ENERGY, "new_energy");
    public static final ItemEntry<Item> FAST_ENERGY = createCustomConduitItem(CustomEnderConduitTypes.FAST_ENERGY, "fast_energy");

    private static ItemEntry<Item> createCustomConduitItem(Supplier<? extends IConduitType<?>> type, String itemName) {
        return REGISTRATE_INSTANCE.item(itemName + "_conduit",
                        p -> ConduitItemFactory.build(type, p))
                .tab(EIOCreativeTabs.CONDUITS)
                .model((ctx, p) -> p.withExistingParent(itemName+"_conduit", EnderIO.loc("item/conduit")).texture("0", type.get().getItemTexture()))
                .register();
    }

    public static Registrate registrate() {
        return REGISTRATE.get();
    }

    public static void register() {}
}
