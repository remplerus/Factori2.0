package com.rempler.factori20.data;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.DataGenerator;
//import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = F20Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class F20DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
//TODO 1.19.3+
 //     PackOutput output = gen.getPackOutput();
        gen.addProvider(event.includeClient(), new F20BlockStateGen(gen, fileHelper));
        gen.addProvider(event.includeClient(), new F20ItemModelGen(gen, fileHelper));
        gen.addProvider(event.includeClient(), new F20LangGen(gen, "en_us"));
    }
}
