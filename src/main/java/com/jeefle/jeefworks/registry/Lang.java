package com.jeefle.jeefworks.registry;

import java.util.Set;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.gregtechceu.gtceu.utils.FormattingUtil;

public class Lang extends com.gregtechceu.gtceu.data.lang.LangHandler {
    private static final Set<Material> MATERIALS = Set.of();

    public static void init(RegistrateLangProvider provider) {
        initItemTooltips(provider);
        LangValues.init(provider);
    }

    private static void initItemTooltips(RegistrateLangProvider provider) {

        // materials
        for (Material material : MATERIALS) {
            provider.add(material.getUnlocalizedName(), FormattingUtil.toEnglishName(material.getName()));
        }

    }
}