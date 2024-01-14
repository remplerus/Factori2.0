package com.rempler.factori20.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class ResearchRecipe implements Recipe<SimpleContainer> {
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int researchTime;

    public ResearchRecipe(ItemStack output, List<Ingredient> recipeItems, int researchTime) {
        this.output = output;
        this.recipeItems = NonNullList.copyOf(recipeItems);
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

        public static final Codec<ResearchRecipe> CODEC = RecordCodecBuilder.create((p_176610_) -> p_176610_.group(
                ItemStack.CODEC.fieldOf("output").forGetter((p_176618_) -> p_176618_.output),
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter((p_176616_) -> p_176616_.recipeItems),
                Codec.INT.fieldOf("researchTime").forGetter((p_176614_) -> p_176614_.researchTime)
        ).apply(p_176610_, ResearchRecipe::new));

        @Override
        public @Nonnull ResearchRecipe fromNetwork(FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack output = buf.readItem();
            int researchTime = buf.readInt();
            return new ResearchRecipe(output, inputs,researchTime);
        }

        @Override
        public Codec<ResearchRecipe> codec() {
            return CODEC;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ResearchRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            assert Minecraft.getInstance().level != null;
            buf.writeItem(recipe.getOutput());
            buf.writeInt(recipe.getResearchTime());
        }
    }

}
