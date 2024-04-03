package com.jeefle.jeefworks.data;


import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.common.data.*;
import com.jeefle.jeefworks.Jeefworks;
import com.jeefle.jeefworks.api.machine.multiblock.APartAbility;
import com.jeefle.jeefworks.api.machine.multiblock.VolcanusMachine;
import com.jeefle.jeefworks.registry.JWCreativeTabs;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.api.pattern.Predicates.autoAbilities;
import static com.gregtechceu.gtceu.api.pattern.util.RelativeDirection.*;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static com.jeefle.jeefworks.Jeefworks.REGISTRATE;

public class JWMachines {
    static {
        REGISTRATE.creativeModeTab(() -> JWCreativeTabs.ITEM);
    }

    public static final int[] MV2ZPM = GTValues.tiersBetween(2, 7);
    public static final int[] LV2ZPM = GTValues.tiersBetween(1, 7);
    public static final int[] ULVTier = {GTValues.ULV};

    /* Declaration Example
    public static final MachineDefinition[] DEHYDRATOR =
            registerSimpleMachines("dehydrator", GTRecipeTypes.MACERATOR_RECIPES, GTMachines.defaultTankSizeFunction, ULVTier);
    */

    private static Object APartAbility;
    public static final MultiblockMachineDefinition VOLCANUS = REGISTRATE.multiblock("volcanus", VolcanusMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.BLAST_RECIPES)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start(RIGHT, BACK, UP)
                    .aisle("AASAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("C###C", "#DDD#", "#DED#", "#DDD#", "C###C").setRepeatable(4, 34)
                    .aisle("VVVVV", "VBBBV", "VBBBV", "VBBBV", "VVVVV")
                    .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("V", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(abilities(PartAbility.IMPORT_FLUIDS))
                            .or(abilities(PartAbility.IMPORT_ITEMS)))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(abilities(PartAbility.EXPORT_FLUIDS))
                            .or(abilities(PartAbility.EXPORT_ITEMS))
                            .or(abilities((PartAbility) APartAbility.BLAZE_VENT))
                            .or(autoAbilities(true, false, false)))
                    .where("B", Predicates.blocks(GTNNCasingBlocks.PROCESS_MACHINE_CASING.get()))
                    .where("C", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Steel)))
                    .where("D", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("E", Predicates.blocks(HIGH_SPEED_PIPE_BLOCK.get()))
                    .where("#", Predicates.air())
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                    GTNN.id("block/multiblock/neutron_activator"), false)
            .register();







    public static MachineDefinition[] registerSimpleMachines(String name,
                                                             GTRecipeType recipeType,
                                                             Int2LongFunction tankScalingFunction,
                                                             int... tiers) {
        return registerTieredMachines(name, (holder, tier) -> new SimpleTieredMachine(holder, tier, tankScalingFunction), (tier, builder) -> builder
                .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName(name), VLVT[tier]))
                .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType))
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(recipeType)
                .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                .workableTieredHullRenderer(Jeefworks.id("block/machines/" + name))
                .tooltips(GTMachines.explosion())
                .tooltips(GTMachines.workableTiered(tier, V[tier], V[tier] * 64, recipeType, tankScalingFunction.apply(tier), true))
                .compassNode(name)
                .register(), tiers);
    }

    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for (int tier : tiers) {
            var register = REGISTRATE.machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name, holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static void init() {
    }
}
