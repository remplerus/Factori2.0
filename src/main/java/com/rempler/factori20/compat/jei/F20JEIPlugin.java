package com.rempler.factori20.compat.jei;

import com.rempler.factori20.common.recipe.ResearchRecipe;
import com.rempler.factori20.compat.jei.research.ResearchRecipeCategory;
import com.rempler.factori20.utils.F20Constants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class F20JEIPlugin implements IModPlugin {
    public static RecipeType<ResearchRecipe> RESEARCH_TYPE =
            new RecipeType<>(ResearchRecipeCategory.UID, ResearchRecipe.class);
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(F20Constants.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ResearchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ResearchRecipe> recipesInfusing = rm.getAllRecipesFor(ResearchRecipe.Type.INSTANCE);
        registration.addRecipes(RESEARCH_TYPE, recipesInfusing);
    }
}
