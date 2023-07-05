package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class F20RecipeGen extends RecipeProvider {
    public F20RecipeGen(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        createDrillHeads(consumer);
        createDrills(consumer);
        ShapedRecipeBuilder.shaped(F20Items.SCANNER.get())
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

    private void createDrills(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(F20Items.BURNER_DRILL.get())
                .pattern("CCC")
                .pattern("CcC")
                .pattern("CCC")
                .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('c', Items.CHARCOAL)
                .unlockedBy("charcoal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CHARCOAL))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "drills/burner_drill"));
        ShapedRecipeBuilder.shaped(F20Items.ELECTRIC_DRILL.get())
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

    private void createDrillHeads(Consumer<FinishedRecipe> consumer) {
        createDrillHeadsRecipe(consumer, F20Items.IRON_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_IRON, Items.DIAMOND);
        createDrillHeadsRecipe(consumer, F20Items.GOLD_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_GOLD, F20Items.IRON_DRILL_HEAD.get());
        createDrillHeadsRecipe(consumer, F20Items.DIAMOND_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_DIAMOND, F20Items.GOLD_DRILL_HEAD.get());
        createDrillHeadsRecipe(consumer, F20Items.EMERALD_DRILL_HEAD.get(), Tags.Items.STORAGE_BLOCKS_EMERALD, F20Items.DIAMOND_DRILL_HEAD.get());
    }

    private void createDrillHeadsRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, TagKey<Item> input1, ItemLike input2) {
        ShapedRecipeBuilder.shaped(output)
                .pattern("III")
                .pattern("IDI")
                .pattern("III")
                .define('I', input1)
                .define('D', input2)
                .unlockedBy("drill_head", InventoryChangeTrigger.TriggerInstance.hasItems(input2))
                .save(consumer, new ResourceLocation(F20Constants.MODID, "drill_heads/"+output));
    }
}
