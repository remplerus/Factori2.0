package com.rempler.factori20.common.init;

import com.rempler.factori20.utils.F20Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class F20CreativeTab {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, F20Constants.MODID);
    public static final RegistryObject<CreativeModeTab> F20_TAB = TAB.register("factori20", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.factori20"))
            .icon(() -> new ItemStack(F20Items.SCANNER.get()))
            .displayItems((displayParameters, output) -> {
                for (RegistryObject<Item> item : F20Items.ITEMS.getEntries()) {
                    output.accept(item.get());
                }
            }).build());

    public static void register(IEventBus bus) {
        TAB.register(bus);
    }
}
