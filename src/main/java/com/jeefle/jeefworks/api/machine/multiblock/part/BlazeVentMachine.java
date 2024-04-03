package com.jeefle.jeefworks.api.machine.multiblock.part;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;


//mostly stolen from gt--
public class BlazeVentMachine extends FluidHatchPartMachine {

    private static final double discount = 0.7;
    public BlazeVentMachine(IMachineBlockEntity holder, int tier, long initialCapacity, Object... args) {
        super(holder, tier, IO.IN, initialCapacity, 1, args);
    }

    public long getMaxFluidConsume() {
        return Math.round(INITIAL_TANK_CAPACITY_1X * 0.01);
    }

    public long consumeBlaze(){
        if (this.isWorkingEnabled() && this.tank.drain(getMaxFluidConsume(),true).getAmount() == getMaxFluidConsume() && this.tank.getFluidInTank(0).getFluid() == GTMaterials.Blaze.getFluid()){
            return Math.abs(this.tank.drain(getMaxFluidConsume(), false).getAmount());
        } else{
            return 0;
        }
    }

    @Override
    public GTRecipe modifyRecipe(GTRecipe recipe){
        GTRecipe newRecipe = recipe.copy();
        if (true) {
            if (true) {
                long newEu = (long) Math.abs((double) RecipeHelper.getInputEUt(recipe) * discount);
                for (Content content : recipe.getTickInputContents(EURecipeCapability.CAP)) {
                    content.content = newEu;
                }
            }
        }
        return newRecipe;
    }
}
