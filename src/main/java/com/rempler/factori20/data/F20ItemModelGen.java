package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class F20ItemModelGen extends ItemModelProvider {
    public F20ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, F20Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ?> item : F20Items.ITEMS.getEntries()) {
            if (!(item.get() instanceof BlockItem)){
                simpleItem(item);
            }
        }
    }

    private ItemModelBuilder simpleItem(DeferredHolder<Item, ?> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(F20Constants.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredHolder<Item, ?> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(F20Constants.MODID, "item/" + item.getId().getPath()));
    }
}
