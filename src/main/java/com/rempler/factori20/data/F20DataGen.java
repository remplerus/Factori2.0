package com.rempler.factori20.data;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = F20Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class F20DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();
        gen.addProvider(event.includeClient(), new F20BlockStateGen(output, fileHelper));
        gen.addProvider(event.includeClient(), new F20ItemModelGen(output, fileHelper));
        gen.addProvider(event.includeClient(), new F20LangGen(output, "en_us"));

        gen.addProvider(event.includeServer(), new F20RecipeGen(output, event.getLookupProvider()));
        gen.addProvider(event.includeServer(), F20LootTable.F20LootTableProvider.create(output));
    }
}
