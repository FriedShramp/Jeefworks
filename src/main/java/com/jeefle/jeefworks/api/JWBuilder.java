package com.jeefle.jeefworks.api;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import net.minecraft.resources.ResourceLocation;

public class JWBuilder extends Material.Builder {
    public JWBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public Material.Builder acid() {
        fluid(FluidStorageKeys.LIQUID, new FluidBuilder().attribute(FluidAttributes.ACID));
        return this;
    }
}
