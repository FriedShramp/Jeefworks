package com.jeefle.jeefworks.data.recipes;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.data.recipe.*;
import com.gregtechceu.gtceu.data.recipe.misc.AssemblerRecipeLoader;
import com.jeefle.jeefworks.data.JWCasingBlocks;
import com.jeefle.jeefworks.data.JWMachines;
import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.jeefle.jeefworks.data.JWMaterials.*;

public class JWRecipes {
    public static void init(Consumer<FinishedRecipe> provider){
        VanillaRecipeHelper.addShapedRecipe(provider, "pyrotheic_oven", new ItemStack(JWMachines.VOLCANUS.getItem()),
                "TMT", "ARA", "CBC",
                'C', CustomTags.HV_CIRCUITS,
                'B', GTMachines.THERMAL_CENTRIFUGE[HV].asStack(),
                'R', new UnificationEntry(rotor, StainlessSteel),
                'M', GTItems.ELECTRIC_MOTOR_HV,
                'T', GTBlocks.CASING_STEEL_SOLID.asStack(),
                'A', JWCasingBlocks.VULCANIC_CASING.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, "blaze_vent", new ItemStack(JWMachines.BLAZE_VENT[HV].getItem()),
                "AMA", "ARA", "CBC",
                'C', JWCasingBlocks.VULCANIC_CASING.asStack(),
                'B', new UnificationEntry(rotor, Steel),
                'R', GTMachines.FLUID_IMPORT_HATCH[HV].asStack(),
                'M', new UnificationEntry(frameGt, Vulcanic_Alloy),
                'A', new UnificationEntry(plate, Vulcanic_Alloy));

        ASSEMBLER_RECIPES.recipeBuilder("vulcanic_casing")
                .inputItems(new UnificationEntry(frameGt, Vulcanic_Alloy))
                .inputItems(new UnificationEntry(plate, Vulcanic_Alloy), 6)
                .outputItems(JWCasingBlocks.VULCANIC_CASING.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("vulcanic_dust")
                .inputItems(new UnificationEntry(dust, Olivine))
                .inputItems(new UnificationEntry(dust, BlackBronze), 2)
                .inputItems(new UnificationEntry(dust, Carbon))
                .inputItems(new UnificationEntry(dust, WroughtIron))
                .outputItems(ChemicalHelper.get(dust, Vulcanic_Alloy, 5))
                .duration(500).EUt(16)
                .save(provider);
    }
}
