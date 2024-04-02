package com.jeefle.jeefworks.registry;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.jeefle.jeefworks.Jeefworks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class JWCreativeTabs {
    @SuppressWarnings("null")
    public static RegistryEntry<CreativeModeTab> ITEM = Jeefworks.REGISTRATE.defaultCreativeTab(Jeefworks.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(Jeefworks.MOD_ID, Jeefworks.REGISTRATE))
                            .icon(GTItems.DUCT_TAPE::asStack)
                            .title(Component.literal("Jeefworks"))
                            .build())
            .register();

    public static void init() {

    }
}