package com.rempler.factori20.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ResearchRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int researchTime;

    public ResearchRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int researchTime) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.researchTime = researchTime;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        boolean truege1 = false;
        boolean truege2 = false;
        boolean truege3 = false;
        for (int i = 0; i < pContainer.getContainerSize(); ++i) {
            if (recipeItems.get(0).test(pContainer.getItem(i))) {
                truege1 = true;
            }
            if (recipeItems.get(0).test(pContainer.getItem(i))) {
                truege2 = true;
            }
            if (recipeItems.get(0).test(pContainer.getItem(i))) {
                truege3 = true;
            }
        }
        return truege1&&truege2&&truege3;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public int getResearchTime() {
        return researchTime;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.getResultItem(pRegistryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ResearchRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "research";
    }


    public static class Serializer implements RecipeSerializer<ResearchRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(F20Constants.MODID, Type.ID);

        @Override
        public ResearchRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int researchTime = GsonHelper.getAsInt(pSerializedRecipe, "researchTime", 2500);

            return new ResearchRecipe(pRecipeId, output, inputs, researchTime);
        }

        @Override
        public @Nullable ResearchRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack output = buf.readItem();
            int researchTime = buf.readInt();
            return new ResearchRecipe(id, output, inputs,researchTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ResearchRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            assert Minecraft.getInstance().level != null;
            buf.writeItemStack(recipe.getOutput(), false);
            buf.writeInt(recipe.getResearchTime());
        }
    }

}
