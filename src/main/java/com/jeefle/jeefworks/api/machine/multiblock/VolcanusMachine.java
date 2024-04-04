package com.jeefle.jeefworks.api.machine.multiblock;


import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.jeefle.jeefworks.api.machine.feature.IGTPPMachine;
import com.jeefle.jeefworks.api.machine.multiblock.part.BlazeVentMachine;
import com.lowdragmc.lowdraglib.side.item.IItemTransfer;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;

//mostly from gt-- thank you arbor :)
public class VolcanusMachine extends CoilWorkableElectricMultiblockMachine implements IGTPPMachine {

    private static final double discount = 0.7;
    private boolean isWorking;
    private Set<BlazeVentMachine> blazeVents;
    protected ConditionalSubscriptionHandler blazeVentSubs;
    public VolcanusMachine(IMachineBlockEntity holder) {
        super(holder);
        this.blazeVentSubs = new ConditionalSubscriptionHandler(this, this::blazeUpdate, this :: isFormed);
    }

    @Override
    public void onStructureFormed() {
        // Declare 'height' as a local variable if not used elsewhere
        int height = 0;
        super.onStructureFormed();

        // Cache the Map access to avoid repeated calls
        var matchContext = getMultiblockState().getMatchContext();
        Map<Long, IO> ioMap = matchContext.getOrCreate("ioMap", Long2ObjectMaps::emptyMap);

        // Cache the result of getParts() to prevent repetitive calls
        List<IMultiPart> parts = getParts();
        for (IMultiPart part : parts) {
            IO io = ioMap.getOrDefault(part.self().getPos().asLong(), IO.BOTH);
            if (io == IO.NONE) continue;

            for (var handler : part.getRecipeHandlers()) {
                var handlerIO = handler.getHandlerIO();
                if (io != IO.BOTH && handlerIO != IO.BOTH && io != handlerIO) continue;
                if (handler.getCapability() == EURecipeCapability.CAP && handler instanceof IEnergyContainer) {

                }
                if (handler.getCapability() == ItemRecipeCapability.CAP && handler instanceof IItemTransfer) {
                    if (handlerIO == IO.IN || handlerIO == IO.BOTH) {
                    }
                }
            }
            if (part instanceof ItemBusPartMachine itemBusPartMachine) {
            }

            if (part instanceof BlazeVentMachine blazeVent) {
                blazeVents = APartAbility.getOrDefault(blazeVents, HashSet::new);
                blazeVents.add(blazeVent);
            }
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        blazeVents = null;
    }

    public boolean hasBlaze(){
        return this.isWorking;
    }


    protected void blazeUpdate(){
        if (blazeVents == null) return;

        boolean anyWorking = false;

        for (var vent : blazeVents) {
            long increase = vent.consumeBlaze();
            if (increase > 0) {
                anyWorking = true;
            }
        }
        this.isWorking = anyWorking;
    }

    /*
    @Override
    protected @Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        GTRecipe newRecipe = recipe.copy();
        if (true) {
            if (true) {
                long newEu = (long) Math.abs((double) RecipeHelper.getInputEUt(recipe) * discount);
                for (Content content : recipe.getTickInputContents(EURecipeCapability.CAP)) {
                    content.content = newEu;
                }
            }
        }
        return super.getRealRecipe(newRecipe);
    }
    */


    //////////////////////////////////////
    //******     RECIPE LOGIC    *******//
    //////////////////////////////////////

    @Nullable
    public static GTRecipe volcanusRecipe(MetaMachine machine, @Nonnull GTRecipe recipe) {
        if (machine instanceof VolcanusMachine volcanus) {
            if (volcanus.hasBlaze()){
                return recipe;
            }
            return null;
        }
        throw new RuntimeException("Machine is not a Volcanus");
    }

    @Override
    protected @org.jetbrains.annotations.Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        return volcanusRecipe(this, recipe);
    }

    @Override
    public int getCoilTier(){
        return 10;
    }
}
