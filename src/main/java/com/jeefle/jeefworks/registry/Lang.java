package com.jeefle.jeefworks.registry;

import java.util.Set;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jeefle.jeefworks.data.JWMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import static com.jeefle.jeefworks.data.JWMaterials.*;

public class Lang extends com.gregtechceu.gtceu.data.lang.LangHandler {
    private static final Set<Material> MATERIALS = Set.of(
            Vulcanic_Alloy
    );

    public static void init(RegistrateLangProvider provider) {
        initItemTooltips(provider);
        LangValues.init(provider);
    }

    private static void initItemTooltips(RegistrateLangProvider provider) {

        JWMaterials.init();

        for (Material material : MATERIALS) {
            provider.add(material.getUnlocalizedName(), FormattingUtil.toEnglishName(material.getName()));
        }

    }
}