package com.rempler.factori20.compat.rei;

import com.rempler.factori20.compat.rei.displays.ResearchDisplay;
import com.rempler.factori20.utils.F20Constants;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;

@REIPluginCommon
public class F20REIServerPlugin implements BuiltinPlugin, REIServerPlugin {
    public static CategoryIdentifier<ResearchDisplay> RESEARCH = CategoryIdentifier.of(F20Constants.MODID, "plugins/research");
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(RESEARCH, ResearchDisplay.serializer(ResearchDisplay::new));
    }
}
