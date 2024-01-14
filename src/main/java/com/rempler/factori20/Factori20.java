package com.rempler.factori20;

import com.mojang.logging.LogUtils;
import com.rempler.factori20.api.chunk.ResourceConfig;
import com.rempler.factori20.common.init.*;
import com.rempler.factori20.utils.F20Config;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

import java.nio.file.Path;

@Mod(F20Constants.MODID)
public class Factori20 {
    public static final Logger LOGGER = LogUtils.getLogger();

    public Factori20(IEventBus bus) {
        Path path = FMLPaths.CONFIGDIR.get().resolve(F20Constants.MODID).toAbsolutePath().normalize();
        new ResourceConfig(path);
        F20Config.loadConfig(F20Config.COMMON_CONFIG, path.resolve("config.toml"));
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, F20Config.COMMON_CONFIG);
        F20Items.register(bus);
        F20Blocks.register(bus);
        F20BEs.register(bus);
        F20Menus.register(bus);
        F20CreativeTab.register(bus);
        F20Recipes.register(bus);
        bus.addListener(this::addCreativeTab);
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == F20CreativeTab.F20_TAB.get()) {
            for (DeferredHolder<Item, ?> item : F20Items.ITEMS.getEntries()) {
                event.accept(item.get());
            }
        }
    }
}
