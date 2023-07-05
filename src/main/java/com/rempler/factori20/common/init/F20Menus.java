package com.rempler.factori20.common.init;

import com.rempler.factori20.common.menu.BurnerDrillMenu;
import com.rempler.factori20.common.menu.BurnerResearchMenu;
import com.rempler.factori20.common.menu.ElectricDrillMenu;
import com.rempler.factori20.common.menu.ElectricResearchMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, F20Constants.MODID);
    public static final RegistryObject<MenuType<ElectricDrillMenu>> ELECTRIC_DRILL_CONTAINER = MENU_TYPES.register("electric_drill_menu",
            () -> IForgeMenuType.create(ElectricDrillMenu::new));
    public static final RegistryObject<MenuType<BurnerDrillMenu>> BURNER_DRILL_CONTAINER = MENU_TYPES.register("burner_drill_menu",
            () -> IForgeMenuType.create(BurnerDrillMenu::new));
    public static final RegistryObject<MenuType<ElectricResearchMenu>> ELECTRIC_RESEARCH_CONTAINER = MENU_TYPES.register("electric_research_menu",
            () -> IForgeMenuType.create(ElectricResearchMenu::new));
    public static final RegistryObject<MenuType<BurnerResearchMenu>> BURNER_RESEARCH_CONTAINER = MENU_TYPES.register("burner_research_menu",
            () -> IForgeMenuType.create(BurnerResearchMenu::new));

    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }
}
