package com.rempler.factori20;

import com.rempler.factori20.api.helpers.MessagesHelper;
import com.rempler.factori20.client.drill.BurnerDrillScreen;
import com.rempler.factori20.client.drill.ElectricDrillScreen;
import com.rempler.factori20.client.research.BurnerResearchScreen;
import com.rempler.factori20.client.research.ElectricResearchScreen;
import com.rempler.factori20.common.init.F20Menus;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

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
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        //TODO Network rework
        //event.enqueueWork(MessagesHelper::register);
    }
}
