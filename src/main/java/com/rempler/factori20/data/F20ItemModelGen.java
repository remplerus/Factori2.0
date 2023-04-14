package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Items;
import com.rempler.factori20.common.item.DrillHeadItem;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class F20ItemModelGen extends ItemModelProvider {
    public F20ItemModelGen(DataGenerator output, ExistingFileHelper existingFileHelper) {
        super(output, F20Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : F20Items.ITEMS.getEntries()) {
            if (item.get() instanceof DrillHeadItem) {
                drillHead(item);
            } else {
                simpleItem(item);
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(F20Constants.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(F20Constants.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder drillHead(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(F20Constants.MODID, "item/drill_head"));
    }
}
