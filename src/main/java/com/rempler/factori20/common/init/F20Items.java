package com.rempler.factori20.common.init;

import com.rempler.factori20.common.item.DrillHeadItem;
import com.rempler.factori20.common.item.ScannerItem;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, F20Constants.MODID);
    private static final Item.Properties TAB = new Item.Properties().tab(F20Constants.F20_TAB);

    public static final RegistryObject<DrillHeadItem> IRON_DRILL_HEAD = ITEMS.register("iron_drill_head",
            () -> new DrillHeadItem(TAB,1));
    public static final RegistryObject<DrillHeadItem> DIAMOND_DRILL_HEAD = ITEMS.register("diamond_drill_head",
            () -> new DrillHeadItem(TAB,3));
    public static final RegistryObject<DrillHeadItem> EMERALD_DRILL_HEAD = ITEMS.register("emerald_drill_head",
            () -> new DrillHeadItem(TAB,4));
    public static final RegistryObject<DrillHeadItem> GOLD_DRILL_HEAD = ITEMS.register("gold_drill_head",
            () -> new DrillHeadItem(TAB,2));
    public static final RegistryObject<ScannerItem> SCANNER = ITEMS.register("scanner",
            () -> new ScannerItem(TAB));
    public static final RegistryObject<BlockItem> ELECTRIC_DRILL = ITEMS.register("electric_drill_block",
            () -> new BlockItem(F20Blocks.ELECTRIC_DRILL_BLOCK.get(), TAB));
    public static final RegistryObject<BlockItem> BURNER_DRILL = ITEMS.register("burner_drill_block",
            () -> new BlockItem(F20Blocks.BURNER_DRILL_BLOCK.get(), TAB));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
