package com.rempler.factori20;

import com.mojang.logging.LogUtils;
import com.rempler.factori20.common.init.F20BEs;
import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.common.init.F20Menus;
import com.rempler.factori20.utils.F20Config;
import com.rempler.factori20.utils.F20Constants;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

@Mod(F20Constants.MODID)
public class Factori20 {
    public static final Logger LOGGER = LogUtils.getLogger();

    public Factori20() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, F20Config.COMMON_CONFIG);
        F20Config.loadConfig(F20Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(F20Constants.MODID + "-common.toml"));
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        F20Items.register(bus);
        F20Blocks.register(bus);
        F20BEs.register(bus);
        F20Menus.register(bus);
        // TODO for 1.19.3+
        // bus.addListener(this::addCreativeTab);
    }

    //private void addCreativeTab(CreativeModeTabEvent.BuildContents event) {
    //    if (event.getTab() == F20Constants.F20_TAB) {
    //        for (RegistryObject<Item> item : F20Items.ITEMS.getEntries()) {
    //            event.accept(item.get());
    //        }
    //    }
    //}
}
