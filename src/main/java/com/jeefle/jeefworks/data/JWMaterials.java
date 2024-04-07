package com.jeefle.jeefworks.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.jeefle.jeefworks.api.JWBuilder;
import com.jeefle.jeefworks.registry.JWCreativeTabs;

import java.util.Collection;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

import static com.jeefle.jeefworks.Jeefworks.REGISTRATE;

public class JWMaterials {
    static {
        REGISTRATE.creativeModeTab(() -> JWCreativeTabs.ITEM);
    }

    public static Material Vulcanic_Alloy = Builder("vulcanic")
            .ingot().fluid().dust()
            .blastTemp(3540)
            .color(0x4d322d).iconSet(MaterialIconSet.DULL)
            .appendFlags(EXT_METAL, GENERATE_PLATE, GENERATE_FRAME, GENERATE_SPRING)
            .buildAndRegister();

    public static JWBuilder Builder(String id) {
        return new JWBuilder(GTCEu.id(id));
    }
    public static void init() {}
}
