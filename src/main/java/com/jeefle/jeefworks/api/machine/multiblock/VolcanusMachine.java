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
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.jeefle.jeefworks.api.machine.feature.IGTPPMachine;
import com.jeefle.jeefworks.api.machine.multiblock.part.BlazeVentMachine;
import com.jeefle.jeefworks.api.recipe.BlazeVentCondition;
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
    protected ConditionalSubscriptionHandler blazeVentSubs;
    public VolcanusMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        // Declare 'height' as a local variable if not used elsewhere
        int height = 0;
        super.onStructureFormed();

        // Cache the Map access to avoid repeated calls
        var matchContext = getMultiblockState().getMatchContext();
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
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
    public GTRecipe volcanusRecipe(MetaMachine machine, @Nonnull GTRecipe recipe) {
        GTRecipe newRecipe = recipe.copy();
        if (machine instanceof VolcanusMachine volcanus) {
            newRecipe.conditions.add(new BlazeVentCondition());
                return newRecipe;
        }
        return null;
    }

    @Nullable
    public IMultiPart getVent() {
        for (IMultiPart part : getParts()) {
            if (part instanceof BlazeVentMachine)
                return part;
        }
        return null;
    }

    public int getTemp(){
        int temp = 0;
        for (IMultiPart part : getParts()) {
            if (part instanceof BlazeVentMachine)
                temp = ((BlazeVentMachine) part).getTemp();
        }
        return temp;
    }

    @Override
    protected @org.jetbrains.annotations.Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        recipe = super.getRealRecipe(volcanusRecipe(this, recipe));
        //recipe = blazeVent.doBlaze(recipe);
        return recipe;
    }

    public int getCoilTier() {
        return -1;
    }
}
