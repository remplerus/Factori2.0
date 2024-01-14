package com.rempler.factori20.common.recipe.builder;

import com.rempler.factori20.common.recipe.ResearchRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResearchRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private int time = 2500;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ResearchRecipeBuilder(ItemLike result) {
        this.result = result.asItem();
    }

    public ResearchRecipeBuilder add(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public ResearchRecipeBuilder time(int time) {
        this.time = time;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, Criterion<?> criterion) {
        this.advancement.addCriterion(pCriterionName, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pRecipeId) {
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("No ingredients for research recipe " + pRecipeId + "!");
        }
        if (result.getDefaultInstance().isEmpty()) {
            throw new IllegalStateException("No output for research recipe " + pRecipeId + "!");
        }

        Advancement.Builder advancementBuilder = pRecipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(AdvancementRequirements.Strategy.OR);
        ResearchRecipe recipe = new ResearchRecipe(this.result.getDefaultInstance(), this.ingredients, this.time);

        pRecipeOutput.accept(pRecipeId, recipe, advancementBuilder.build(new ResourceLocation(pRecipeId.getNamespace(), "recipes/researcher/"
                + pRecipeId.getPath())));
    }
}
