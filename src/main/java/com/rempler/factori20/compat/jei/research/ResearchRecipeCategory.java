package com.rempler.factori20.compat.jei.research;

import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.common.recipe.ResearchRecipe;
import com.rempler.factori20.compat.jei.F20JEIPlugin;
import com.rempler.factori20.utils.F20Constants;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ResearchRecipeCategory implements IRecipeCategory<ResearchRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(F20Constants.MODID, "research");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(F20Constants.MODID, "textures/gui/research_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ResearchRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(F20Blocks.ELECTRIC_RESEARCH_BLOCK.get()));
    }
    @Override
    public RecipeType<ResearchRecipe> getRecipeType() {
        return F20JEIPlugin.RESEARCH_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("f20.jei.research.title");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ResearchRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 65, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 35).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 95, 35).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 135, 35).addItemStack(recipe.getOutput());
    }
}
