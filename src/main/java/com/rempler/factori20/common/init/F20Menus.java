package com.rempler.factori20.common.init;

import com.rempler.factori20.common.menu.BurnerDrillContainerMenu;
import com.rempler.factori20.common.menu.ElectricDrillContainerMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, F20Constants.MODID);
    public static final RegistryObject<MenuType<ElectricDrillContainerMenu>> ELECTRIC_DRILL_CONTAINER = MENU_TYPES.register("electric_drill_container",
            () -> IForgeMenuType.create(ElectricDrillContainerMenu::new));
    public static final RegistryObject<MenuType<BurnerDrillContainerMenu>> BURNER_DRILL_CONTAINER = MENU_TYPES.register("burner_drill_container",
            () -> IForgeMenuType.create(BurnerDrillContainerMenu::new));

    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }
}
