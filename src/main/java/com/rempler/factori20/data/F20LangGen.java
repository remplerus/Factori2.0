package com.rempler.factori20.data;

import com.rempler.factori20.api.helpers.WordHelper;
import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.common.item.WIPItem;
import com.rempler.factori20.utils.F20Constants;
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
            Item getitem = item.get();
            if (getitem instanceof WIPItem wip) {
                add(wip, WordHelper.capitalizeFully(wip.toString().replace("_", " ")) + " (WIP)");
            } else {
                String name = item.get().toString();
                if (name.contains("_1_")) {
                    name = name.replace("research_1_", "logistics_research_");
                } else if (name.contains("_2_")) {
                    name = name.replace("research_2_", "mechanics_research_");
                } else if (name.contains("_3_")) {
                    name = name.replace("research_3_", "production_research_");
                } else if (name.contains("_4_")) {
                    name = name.replace("research_4_", "utility_research_");
                } else if (name.contains("_5_")) {
                    name = name.replace("research_5_", "chemical_research_");
                } else if (name.contains("_6_")) {
                    name = name.replace("research_6_", "quantum_research_");
                }
                if (name.contains("_1")) {
                    name = name.replace("_1", "");
                } else if (name.contains("_8")) {
                    name = "8 " + name.replace("_8", "");
                } else if (name.contains("_64")) {
                    name = "64 " + name.replace("_64", "");
                }
                add(item.get(), WordHelper.capitalizeFully(name.replace("_", " ")));
            }
        }
        add("tooltip.f20.energyStored", "%d/%d FE");
        add("txt.f20.scanner.on_cooldown", "Scanner is for %ds on cooldown");
        add("txt.f20.burn_time", "Burntime: %ds");
        add("txt.f20.visualizer_amt", "Ore: %d/%d");
        add("txt.f20.scan_results_header", "-----Scan Results-----");
        add("txt.f20.ores_available", "%d: %d available");
        add("txt.f20.no_ores_available", "No ores in this chunk");
        add("f20.container.drill", "");
        add("f20.container.researcher", "");
        add("f20.jei.research.title", "Researcher");
        add("itemGroup."+F20Constants.MODID, "Factori20");
    }
}
