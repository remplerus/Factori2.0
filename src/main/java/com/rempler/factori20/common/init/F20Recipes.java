package com.rempler.factori20.common.init;

import com.rempler.factori20.common.recipe.ResearchRecipe;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Recipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, F20Constants.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, F20Constants.MODID);

    public static final RegistryObject<RecipeSerializer<ResearchRecipe>> RESEARCH_SERIALIZER =
            SERIALIZERS.register("research", () -> ResearchRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<ResearchRecipe>> RESEARCH_RECIPE =
            RECIPE_TYPES.register("research", () -> ResearchRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
