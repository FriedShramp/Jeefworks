package com.jeefle.jeefworks;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.jeefle.jeefworks.api.recipe.BlazeVentCondition;
import com.jeefle.jeefworks.data.JWCasingBlocks;
import com.jeefle.jeefworks.data.recipes.JWRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class JWAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return Jeefworks.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return Jeefworks.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        //CustomTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        JWRecipes.init(provider);
    }

    @Override
    public void registerRecipeConditions() {
        GTRegistries.RECIPE_CONDITIONS.register(BlazeVentCondition.INSTANCE.getType(), BlazeVentCondition.class);
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        JWCasingBlocks.init();
    }

    // If you have custom ingredient types, uncomment this & change to match your capability.
    // KubeJS WILL REMOVE YOUR RECIPES IF THESE ARE NOT REGISTERED.
    /*
    public static final ContentJS<Double> PRESSURE_IN = new ContentJS<>(NumberComponent.ANY_DOUBLE, GregitasRecipeCapabilities.PRESSURE, false);
    public static final ContentJS<Double> PRESSURE_OUT = new ContentJS<>(NumberComponent.ANY_DOUBLE, GregitasRecipeCapabilities.PRESSURE, true);

    @Override
    public void registerRecipeKeys(KJSRecipeKeyEvent event) {
        event.registerKey(CustomRecipeCapabilities.PRESSURE, Pair.of(PRESSURE_IN, PRESSURE_OUT));
    }
    */
}
