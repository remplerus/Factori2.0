package com.rempler.factori20.compat.eio;

import com.rempler.factori20.utils.F20Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = F20Constants.MODID)
public class CustomEIOEventBus {
    @SubscribeEvent
    public static void onConstruct(FMLConstructModEvent event) {
        CustomConduitItems.register();
    }
}
