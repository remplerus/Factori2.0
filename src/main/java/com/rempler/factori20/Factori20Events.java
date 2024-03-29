package com.rempler.factori20;

import com.rempler.factori20.api.chunk.ChunkResourceCapability;
import com.rempler.factori20.api.helpers.MessagesHelper;
import com.rempler.factori20.client.drill.BurnerDrillScreen;
import com.rempler.factori20.client.drill.ElectricDrillScreen;
import com.rempler.factori20.client.research.BurnerResearchScreen;
import com.rempler.factori20.client.research.ElectricResearchScreen;
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
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = F20Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Factori20Events {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(F20Menus.ELECTRIC_DRILL_CONTAINER.get(), ElectricDrillScreen::new);
        MenuScreens.register(F20Menus.BURNER_DRILL_CONTAINER.get(), BurnerDrillScreen::new);
        MenuScreens.register(F20Menus.ELECTRIC_RESEARCH_CONTAINER.get(), ElectricResearchScreen::new);
        MenuScreens.register(F20Menus.BURNER_RESEARCH_CONTAINER.get(), BurnerResearchScreen::new);
    }

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(ChunkResourceCapability.class);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(MessagesHelper::register);
    }

    @Mod.EventBusSubscriber
    public static class SpecialEvents {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
            if (!event.getObject().isEmpty()) {
                event.addCapability(new ResourceLocation(F20Constants.MODID, "chunk_resource_data"), new ChunkResourceCapability());
            }
        }
    }
}
