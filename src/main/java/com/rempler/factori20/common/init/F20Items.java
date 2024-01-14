package com.rempler.factori20.common.init;

import com.rempler.factori20.common.item.DrillHeadItem;
import com.rempler.factori20.common.item.ResearchItem;
import com.rempler.factori20.common.item.ScannerItem;
import com.rempler.factori20.common.item.WIPItem;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class F20Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, F20Constants.MODID);
    private static final Item.Properties TAB = new Item.Properties();

    public static final DeferredHolder<Item, DrillHeadItem> IRON_DRILL_HEAD = ITEMS.register("iron_drill_head",
            () -> new DrillHeadItem(TAB,1));
    public static final DeferredHolder<Item, DrillHeadItem> DIAMOND_DRILL_HEAD = ITEMS.register("diamond_drill_head",
            () -> new DrillHeadItem(TAB,3));
    public static final DeferredHolder<Item, DrillHeadItem> EMERALD_DRILL_HEAD = ITEMS.register("emerald_drill_head",
            () -> new DrillHeadItem(TAB,4));
    public static final DeferredHolder<Item, DrillHeadItem> GOLD_DRILL_HEAD = ITEMS.register("gold_drill_head",
            () -> new DrillHeadItem(TAB,2));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_1 = ITEMS.register("research_1_1",
            () -> new ResearchItem(TAB,1, 1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_2 = ITEMS.register("research_2_1",
            () -> new ResearchItem(TAB,2, 1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_3 = ITEMS.register("research_3_1",
            () -> new ResearchItem(TAB,3, 1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_4 = ITEMS.register("research_4_1",
            () -> new ResearchItem(TAB,4, 1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_5 = ITEMS.register("research_5_1",
            () -> new ResearchItem(TAB,5, 1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_6 = ITEMS.register("research_6_1",
            () -> new ResearchItem(TAB,6,1));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_1_8 = ITEMS.register("research_1_8",
            () -> new ResearchItem(TAB,1, 8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_2_8 = ITEMS.register("research_2_8",
            () -> new ResearchItem(TAB,2, 8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_3_8 = ITEMS.register("research_3_8",
            () -> new ResearchItem(TAB,3, 8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_4_8 = ITEMS.register("research_4_8",
            () -> new ResearchItem(TAB,4, 8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_5_8 = ITEMS.register("research_5_8",
            () -> new ResearchItem(TAB,5, 8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_6_8 = ITEMS.register("research_6_8",
            () -> new ResearchItem(TAB,6,8));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_1_64 = ITEMS.register("research_1_64",
            () -> new ResearchItem(TAB,1, 64));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_2_64 = ITEMS.register("research_2_64",
            () -> new ResearchItem(TAB,2, 64));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_3_64 = ITEMS.register("research_3_64",
            () -> new ResearchItem(TAB,3, 64));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_4_64 = ITEMS.register("research_4_64",
            () -> new ResearchItem(TAB,4, 64));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_5_64 = ITEMS.register("research_5_64",
            () -> new ResearchItem(TAB,5, 64));
    public static final DeferredHolder<Item, ResearchItem> RESEARCH_6_64 = ITEMS.register("research_6_64",
            () -> new ResearchItem(TAB,6,64));
    public static final DeferredHolder<Item, ScannerItem> SCANNER = ITEMS.register("scanner",
            () -> new ScannerItem(TAB));
    public static final DeferredHolder<Item, WIPItem> ADV_SCANNER = ITEMS.register("advanced_scanner",
            () -> new WIPItem(TAB));
    public static final DeferredHolder<Item, BlockItem> ELECTRIC_DRILL = ITEMS.register("electric_drill_block",
            () -> new BlockItem(F20Blocks.ELECTRIC_DRILL_BLOCK.get(), TAB));
    public static final DeferredHolder<Item, BlockItem> BURNER_DRILL = ITEMS.register("burner_drill_block",
            () -> new BlockItem(F20Blocks.BURNER_DRILL_BLOCK.get(), TAB));
    public static final DeferredHolder<Item, BlockItem> ELECTRIC_RESEARCH = ITEMS.register("electric_research_block",
            () -> new BlockItem(F20Blocks.ELECTRIC_RESEARCH_BLOCK.get(), TAB));
    public static final DeferredHolder<Item, BlockItem> BURNER_RESEARCH = ITEMS.register("burner_research_block",
            () -> new BlockItem(F20Blocks.BURNER_RESEARCH_BLOCK.get(), TAB));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
