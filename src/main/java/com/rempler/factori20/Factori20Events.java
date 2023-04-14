package com.rempler.factori20;

import com.rempler.factori20.api.chunk.ChunkResourceDataProvider;
import com.rempler.factori20.client.drill.DrillScreen;
import com.rempler.factori20.common.init.F20Menus;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = F20Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Factori20Events {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(F20Menus.DRILL_CONTAINER_MENU.get(), DrillScreen::new);
    }

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(ChunkResourceDataProvider.class);

    }

    //@SubscribeEvent
    //public static void createCreativeTab(CreativeModeTabEvent.Register event) {
    //    F20Constants.F20_TAB = event.registerCreativeModeTab(new ResourceLocation(F20Constants.MODID, "factori0_tab"),
    //            builder -> builder.icon(() -> F20Items.SCANNER.get().getDefaultInstance()).title(Component.literal("Factori20 Tab")).build());
    //}
    @Mod.EventBusSubscriber
    public static class SpecialEvents {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
            if (!event.getObject().isEmpty()) {
                event.addCapability(new ResourceLocation(F20Constants.MODID, "chunk_resource_data"), new ChunkResourceDataProvider());
            }
        }
    }
}
