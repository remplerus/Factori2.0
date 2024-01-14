package com.rempler.factori20.common.init;

import com.rempler.factori20.common.menu.BurnerDrillMenu;
import com.rempler.factori20.common.menu.BurnerResearchMenu;
import com.rempler.factori20.common.menu.ElectricDrillMenu;
import com.rempler.factori20.common.menu.ElectricResearchMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class F20Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, F20Constants.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<ElectricDrillMenu>> ELECTRIC_DRILL_CONTAINER = MENU_TYPES.register("electric_drill_menu",
            () -> IMenuTypeExtension.create(ElectricDrillMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<BurnerDrillMenu>> BURNER_DRILL_CONTAINER = MENU_TYPES.register("burner_drill_menu",
            () -> IMenuTypeExtension.create(BurnerDrillMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ElectricResearchMenu>> ELECTRIC_RESEARCH_CONTAINER = MENU_TYPES.register("electric_research_menu",
            () -> IMenuTypeExtension.create(ElectricResearchMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<BurnerResearchMenu>> BURNER_RESEARCH_CONTAINER = MENU_TYPES.register("burner_research_menu",
            () -> IMenuTypeExtension.create(BurnerResearchMenu::new));

    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }
}
