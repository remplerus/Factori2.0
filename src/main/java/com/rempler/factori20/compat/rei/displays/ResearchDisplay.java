package com.rempler.factori20.compat.rei.displays;

import com.rempler.factori20.common.recipe.ResearchRecipe;
import com.rempler.factori20.compat.rei.F20REIServerPlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;

public class ResearchDisplay extends BasicDisplay implements SimpleGridMenuDisplay {

    public ResearchDisplay(RecipeHolder<? extends ResearchRecipe> recipe) {
        this(EntryIngredients.ofIngredients(recipe.value().getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.value().getOutput())));
    }
    public ResearchDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ResearchDisplay(List<EntryIngredient> entryIngredients, List<EntryIngredient> entryIngredients1, CompoundTag compoundTag) {
        super(entryIngredients, entryIngredients1);
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return F20REIServerPlugin.RESEARCH;
    }

    public static <R extends ResearchDisplay> BasicDisplay.Serializer<R> serializer(BasicDisplay.Serializer.RecipeLessConstructor<R> constructor) {
        return BasicDisplay.Serializer.ofRecipeLess(constructor);
    }
}
