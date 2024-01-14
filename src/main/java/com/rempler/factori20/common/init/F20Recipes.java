package com.rempler.factori20.common.init;

import com.rempler.factori20.common.recipe.ResearchRecipe;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class F20Recipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, F20Constants.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, F20Constants.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ResearchRecipe>> RESEARCH_SERIALIZER =
            SERIALIZERS.register("research", () -> ResearchRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ResearchRecipe>> RESEARCH_RECIPE =
            RECIPE_TYPES.register("research", () -> ResearchRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
