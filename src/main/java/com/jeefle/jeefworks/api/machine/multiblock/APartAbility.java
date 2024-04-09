package com.jeefle.jeefworks.api.machine.multiblock;


import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import javax.annotation.Nullable;
import java.util.function.Supplier;

//stolen from gt--
public class APartAbility extends PartAbility {

    public static final PartAbility BLAZE_VENT = new PartAbility("blaze_vent");
    public static final PartAbility SUBSTRATE_HATCH = new PartAbility("substrate_hatch");
    public APartAbility(String name) {
        super(name);
    }

    public static <T> T getOrDefault(@Nullable T value, Supplier<T> defaultSupplier) {
        if (value == null) return defaultSupplier.get();
        return value;
    }
}