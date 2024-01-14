package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.common.item.ResearchItem;
import com.rempler.factori20.common.recipe.builder.ResearchRecipeBuilder;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class F20RecipeGen extends RecipeProvider {
    private final RecipeCategory ignored = RecipeCategory.MISC;
    public F20RecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        createDrillHeads(consumer);
        createDrills(consumer);
        createResearchRecipe(consumer);
        createResearcherRecipe(consumer);
        ShapedRecipeBuilder.shaped(ignored, F20Items.SCANNER.get())
                .pattern("gg ")
                .pattern("crc")
                .pattern(" ii")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('c', Tags.Items.INGOTS_COPPER)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                .save(consumer);
    }

    private void createResearcherRecipe(RecipeOutput consumer) {
        new ResearchRecipeBuilder(F20Items.RESEARCH_1.get())
                .add(Ingredient.of(Items.IRON_INGOT))
                .add(Ingredient.of(Items.GOLD_INGOT))
                .add(Ingredient.of(Items.DIAMOND))
                .time(2500)
                .unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_1"));
        new ResearchRecipeBuilder(F20Items.RESEARCH_2.get())
                .add(Ingredient.of(Items.EMERALD))
                .add(Ingredient.of(Items.GOLD_INGOT))
                .add(Ingredient.of(Items.DIAMOND))
                .time(7500)
                .unlockedBy("gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_2"));
        new ResearchRecipeBuilder(F20Items.RESEARCH_3.get())
                .add(Ingredient.of(Items.EMERALD))
                .add(Ingredient.of(Items.NETHERITE_INGOT))
                .add(Ingredient.of(Items.DIAMOND))
                .time(15000)
                .unlockedBy("diamond", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_3"));
        new ResearchRecipeBuilder(F20Items.RESEARCH_4.get())
                .add(Ingredient.of(Items.EMERALD))
                .add(Ingredient.of(Items.NETHERITE_INGOT))
                .add(Ingredient.of(Items.IRON_BLOCK))
                .time(30000)
                .unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BLOCK))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_4"));
        new ResearchRecipeBuilder(F20Items.RESEARCH_5.get())
                .add(Ingredient.of(Items.IRON_BLOCK))
                .add(Ingredient.of(Items.DIAMOND_BLOCK))
                .add(Ingredient.of(Items.EMERALD_BLOCK))
                .time(60000)
                .unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BLOCK))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_5"));
        new ResearchRecipeBuilder(F20Items.RESEARCH_6.get())
                .add(Ingredient.of(Items.DIAMOND_BLOCK))
                .add(Ingredient.of(Items.EMERALD_BLOCK))
                .add(Ingredient.of(Items.NETHERITE_BLOCK))
                .time(120000)
                .unlockedBy("netherite", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_BLOCK))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "researcher/research_6"));
    }

    private void createDrills(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(ignored, F20Items.BURNER_DRILL.get())
                .pattern("CCC")
                .pattern("CcC")
                .pattern("CCC")
                .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('c', Items.CHARCOAL)
                .unlockedBy("charcoal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CHARCOAL))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "drills/burner_drill"));
        ShapedRecipeBuilder.shaped(ignored, F20Items.ELECTRIC_DRILL.get())
                .pattern("CrC")
                .pattern("rcr")
                .pattern("IRI")
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('c', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .unlockedBy("redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "drills/electric_drill"));
    }

    private void createDrillHeads(RecipeOutput consumer) {
        createDrillHeadsRecipe(consumer, F20Items.IRON_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_IRON, Items.DIAMOND);
        createDrillHeadsRecipe(consumer, F20Items.GOLD_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_GOLD, F20Items.IRON_DRILL_HEAD.get());
        createDrillHeadsRecipe(consumer, F20Items.DIAMOND_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_DIAMOND, F20Items.GOLD_DRILL_HEAD.get());
        createDrillHeadsRecipe(consumer, F20Items.EMERALD_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_EMERALD, F20Items.DIAMOND_DRILL_HEAD.get());
    }

    private void createDrillHeadsRecipe(RecipeOutput consumer, ItemLike output, TagKey<Item> input1, ItemLike input2) {
        ShapedRecipeBuilder.shaped(ignored, output)
                .pattern("III")
                .pattern("IDI")
                .pattern("III")
                .define('I', input1)
                .define('D', input2)
                .unlockedBy("drill_head", InventoryChangeTrigger.TriggerInstance.hasItems(input2))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "drill_heads/"+output.toString().replace("factori20:", "").replace("_drill_head", "")));
    }

    private void createResearchRecipe(RecipeOutput consumer) {
        for (DeferredHolder<Item, ?> item : F20Items.ITEMS.getEntries()) {
            if (item.get() instanceof ResearchItem ri && ri.getTemp() == 8) {
                createSimpleResearchRecipe(consumer, ri);
            }
        }
    }

    private void createSimpleResearchRecipe(RecipeOutput consumer, ResearchItem ri) {
        ShapedRecipeBuilder.shaped(ignored, ri.getItem(ri.getTemp(), ri.getTier()))
                .pattern("xxx")
                .pattern("x x")
                .pattern("xxx")
                .define('x', ri.getItem(ri.getTemp()/8, ri.getTier()))
                .unlockedBy("research_"+ri.getTier(), InventoryChangeTrigger.TriggerInstance.hasItems(ri))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "research/"+ri.getTier()+"_"+ri.getTemp()));

        ShapelessRecipeBuilder.shapeless(ignored, ri.getItem(ri.getTemp()/8, ri.getTier()), 8)
                .requires(ri.getItem(ri.getTemp(), ri.getTier()))
                .unlockedBy("research_"+ri.getTier(), InventoryChangeTrigger.TriggerInstance.hasItems(ri))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "research/reverse_"+ri.getTier()+"_"+(ri.getTemp())));

        ShapedRecipeBuilder.shaped(ignored, ri.getItem(ri.getTemp()*8, ri.getTier()))
                .pattern("xxx")
                .pattern("x x")
                .pattern("xxx")
                .define('x', ri.getItem(ri.getTemp(), ri.getTier()))
                .unlockedBy("research_"+ri.getTier(), InventoryChangeTrigger.TriggerInstance.hasItems(ri))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "research/"+ri.getTier()+"_"+(ri.getTemp()*8)));

        ShapelessRecipeBuilder.shapeless(ignored, ri.getItem(ri.getTemp(), ri.getTier()), 8)
                .requires(ri.getItem(ri.getTemp()*8, ri.getTier()))
                .unlockedBy("research_"+ri.getTier(), InventoryChangeTrigger.TriggerInstance.hasItems(ri))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "research/reverse_"+ri.getTier()+"_"+ri.getTemp()*8));
    }
}
