package com.rempler.factori20.data;

import com.rempler.factori20.api.helpers.WordHelper;
import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.common.item.WIPItem;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class F20LangGen extends LanguageProvider {
    public F20LangGen(PackOutput output, String locale) {
        super(output, F20Constants.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        for (RegistryObject<Item> item : F20Items.ITEMS.getEntries()) {
            if (item.get() instanceof WIPItem wip) {
                add(wip, WordHelper.capitalizeFully(wip.toString().replace("_", " ")) + " (WIP)");
            } else {
                add(item.get(), WordHelper.capitalizeFully(item.get().toString().replace("_", " ")));
            }
        }
        add("tooltip.f20.energyStored", "%d/%d FE");
        add("txt.f20.scanner.on_cooldown", "Scanner is for %ds on cooldown");
        add("txt.f20.burn_time", "Burntime: %ds");
        add("txt.f20.visualizer_amt", "Ore: %d/%d");
        add("txt.f20.ores_available", "%d: %d available");
        add("txt.f20.no_ores_available", "No ores in this chunk");
        add("f20.container.drill", "");
        add("f20.container.researcher", "");
        add("itemGroup."+F20Constants.MODID, "Factori20");
    }
}
