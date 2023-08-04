package com.rempler.factori20.compat.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.rempler.factori20.common.recipe.ResearchRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Optional;

@IRecipeHandler.For(ResearchRecipe.class)
@ZenRegister
@ZenCodeType.Name("mods.factori20.ResearchManager")
@Document("mods/Factori20/ResearchManager")
public class CTResearchManager implements IRecipeManager, IRecipeHandler<ResearchRecipe> {
    @Override
    public String dumpToCommandString(IRecipeManager<? super ResearchRecipe> manager, ResearchRecipe recipe) {
        return manager.getCommandString() + recipe.getId().getPath() + recipe.getOutput() + "[" + recipe.getIngredients() + "]";
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ResearchRecipe> manager, ResearchRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ResearchRecipe> manager, ResearchRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<ResearchRecipe> recompose(IRecipeManager<? super ResearchRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public RecipeType getRecipeType() {
        return ResearchRecipe.Type.INSTANCE;
    }

    /**
     * Adds a recipe to the Research Table.
     *
     * @param name        Name of the recipe to add.
     * @param output      Output of the recipe.
     * @param inputs      Inputs of the recipe.
     * @param researchTime Time it takes to research the recipe.
     */
    @ZenCodeType.Method
    public void createRecipe(String name, IItemStack output, IIngredient[] inputs, int researchTime) {
        name = fixRecipeName(name);
        ResourceLocation id = new ResourceLocation("crafttweaker", name);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, new ResearchRecipe(id, output.getInternal(),
                (NonNullList<Ingredient>) Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toList(),
                researchTime), ""));
    }
}
