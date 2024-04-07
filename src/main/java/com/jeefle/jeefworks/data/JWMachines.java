package com.jeefle.jeefworks.data;


import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.IMiner;
import com.gregtechceu.gtceu.api.capability.PlatformEnergyCompat;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.DrumMachineItem;
import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.*;
import com.gregtechceu.gtceu.api.machine.steam.SimpleSteamMachine;
import com.gregtechceu.gtceu.api.machine.steam.SteamBoilerMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.api.registry.registrate.MultiblockMachineBuilder;
import com.gregtechceu.gtceu.client.TooltipHelper;
import com.gregtechceu.gtceu.client.renderer.machine.*;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.electric.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.*;
import com.gregtechceu.gtceu.common.machine.multiblock.generator.LargeCombustionEngineMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.generator.LargeTurbineMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;
import com.gregtechceu.gtceu.common.machine.multiblock.primitive.CokeOvenMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.primitive.PrimitiveBlastFurnaceMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.primitive.PrimitivePumpMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.LargeBoilerMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gtceu.common.machine.steam.SteamLiquidBoilerMachine;
import com.gregtechceu.gtceu.common.machine.steam.SteamMinerMachine;
import com.gregtechceu.gtceu.common.machine.steam.SteamSolidBoilerMachine;
import com.gregtechceu.gtceu.common.machine.steam.SteamSolarBoiler;
import com.gregtechceu.gtceu.common.machine.storage.*;
import com.gregtechceu.gtceu.common.pipelike.fluidpipe.longdistance.LDFluidEndpointMachine;
import com.gregtechceu.gtceu.common.pipelike.item.longdistance.LDItemEndpointMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.integration.ae2.GTAEMachines;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryInfo;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.jeefle.jeefworks.Jeefworks;
import com.jeefle.jeefworks.api.machine.multiblock.APartAbility;
import com.jeefle.jeefworks.api.machine.multiblock.VolcanusMachine;
import com.jeefle.jeefworks.api.machine.multiblock.part.BlazeVentMachine;
import com.jeefle.jeefworks.registry.JWCreativeTabs;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static com.jeefle.jeefworks.Jeefworks.REGISTRATE;

public class JWMachines {
    static {
        REGISTRATE.creativeModeTab(() -> JWCreativeTabs.ITEM);
    }

    public static final int[] MV2ZPM = GTValues.tiersBetween(2, 7);
    public static final int[] LV2ZPM = GTValues.tiersBetween(1, 7);
    public static final int[] ULVTier = {GTValues.ULV};
    public static final int[] HVTier = {HV};

    /* Declaration Example
    public static final MachineDefinition[] DEHYDRATOR =
            registerSimpleMachines("dehydrator", GTRecipeTypes.MACERATOR_RECIPES, GTMachines.defaultTankSizeFunction, ULVTier);
    */

    public static final MachineDefinition[] BLAZE_VENT = registerBlazeVents("blaze_vent", "Blaze Heat Vent", Jeefworks.id("block/machine/blaze_vent"), 20000, HVTier);


    public static final MultiblockMachineDefinition VOLCANUS = REGISTRATE.multiblock("volcanus", VolcanusMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifier(GTRecipeModifiers.PARALLEL_HATCH.apply(OverclockingLogic.PERFECT_OVERCLOCK, (oc) -> GTRecipeModifiers::ebfOverclock))
            .appearanceBlock(JWCasingBlocks.VULCANIC_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" V V "," V V "," V V ", "  V  ", "  V  ","     ")
                    .aisle("V   V","V   V","VAAAV", " nnn ", " nnn ","  V  ")
                    .aisle("     ","  b  "," A#A ", "Vn#nV", "VnMnV"," V V ")
                    .aisle("V   V","V   V","VASAV", " nnn ", " nnn ","  V  ")
                    .aisle(" V V "," V V "," V V ", "  V  ", "  V  ","     ")
                    .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("A", Predicates.blocks(JWCasingBlocks.VULCANIC_CASING.get())
                            .or(autoAbilities(definition.getRecipeTypes()))
                            .or(autoAbilities(true, false, false)))
                    .where("M", abilities(PartAbility.MUFFLER))
                    .where("b", abilities(APartAbility.BLAZE_VENT))
                    .where("#", Predicates.air())
                    .where("n", Predicates.blocks(JWCasingBlocks.VULCANIC_CASING.get()))
                    .where("c", heatingCoils())
                    .where("V", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(
                    Jeefworks.id("block/casings/vulcanic_casing"),
                    Jeefworks.id("block/multiblock/volcanus"), false)
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                            Component.translatable(FormattingUtil.formatNumbers(4000) + "K").setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
                }
            })
            .tooltips(Component.translatable("jeefworks.volcanus.tooltip1"))
            .langValue("Octan™ Pyrotheic Oven")
            .compassNodeSelf()
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

    private static MachineDefinition[] registerBlazeVents(String name, String displayname, ResourceLocation model, long initialCapacity, int[] tiers) {
        return registerTieredMachines(name,
                (holder, tier) -> new BlazeVentMachine(holder, tier, initialCapacity),
                (tier, builder) -> {
                    builder.langValue(displayname)
                            .rotationState(RotationState.ALL)
                            .workableCasingRenderer(Jeefworks.id("block/casings/vulcanic_casing"), model)
                            .abilities(APartAbility.BLAZE_VENT)
                            .compassNode("fluid_hatch")
                            .tooltips(Component.translatable("jeefworks.blaze_vent"));
                    return builder.register();
                }, tiers);
    }

    public static void init() {
    }
}
