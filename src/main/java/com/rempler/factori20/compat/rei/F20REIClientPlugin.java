package com.rempler.factori20.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;

public class F20REIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        REIClientPlugin.super.registerCategories(registry);
    }

    //@Override
    //public void registerDisplays(DisplayRegistry registry) {
    //    registry.registerRecipeFiller(ResearchRecipe::new, ResearchRecipe.Type.INSTANCE, ResearchDisplay::new);
    //}
//
    //@Override
    //public void registerScreens(ScreenRegistry registry) {
    //    REIClientPlugin.super.registerScreens(registry);
    //}
//
    //@Override
    //public void registerTransferHandlers(TransferHandlerRegistry registry) {
    //    REIClientPlugin.super.registerTransferHandlers(registry);
    //}

    @Override
    public double getPriority() {
        return -101;
    }
}
