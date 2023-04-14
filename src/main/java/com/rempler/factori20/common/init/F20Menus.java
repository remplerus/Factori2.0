package com.rempler.factori20.common.init;

import com.rempler.factori20.common.menu.DrillContainerMenu;
import com.rempler.factori20.common.menu.VisualizerContainerMenu;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class F20Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, F20Constants.MODID);
    public static final RegistryObject<MenuType<DrillContainerMenu>> DRILL_CONTAINER_MENU = MENU_TYPES.register("drill_container",
            () -> IForgeMenuType.create(DrillContainerMenu::new));
    public static final RegistryObject<MenuType<VisualizerContainerMenu>> VISUALIZER_MENU = MENU_TYPES.register("visualizer",
            () -> IForgeMenuType.create(VisualizerContainerMenu::new));

    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }
}
