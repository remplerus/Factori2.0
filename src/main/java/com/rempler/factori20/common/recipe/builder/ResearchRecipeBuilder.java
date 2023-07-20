package com.rempler.factori20.common.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rempler.factori20.common.init.F20Recipes;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
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
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("No ingredients for research recipe " + pRecipeId + "!");
        }
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new ResearchRecipeBuilder.Result(pRecipeId, this.result, this.ingredients, this.time,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/researcher/"
                + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int time;
        private final List<Ingredient> ingredient;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, List<Ingredient> ingredient, int time, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ingredient = ingredient;
            this.time = time;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            for (Ingredient ingredient : this.ingredient) {
                jsonarray.add(ingredient.toJson());
            }

            pJson.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());

            pJson.add("output", jsonobject);
            pJson.addProperty("researchTime", this.time);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(F20Constants.MODID,"researcher/" +
                    ForgeRegistries.ITEMS.getKey(this.result).getPath());
        }

        @Override
        public RecipeSerializer<?> getType() {
            return F20Recipes.RESEARCH_SERIALIZER.get();
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
