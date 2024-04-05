package com.jeefle.jeefworks.api.recipe;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.jeefle.jeefworks.api.machine.multiblock.part.BlazeVentMachine;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlazeVentCondition extends RecipeCondition {
    public final static BlazeVentCondition INSTANCE = new BlazeVentCondition();

    private double blazeConstant = 1;

    public BlazeVentCondition(double blazeConstant) {
        super();
        this.blazeConstant = blazeConstant;
    }

    public BlazeVentCondition() {
        super();
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        MetaMachine machine = recipeLogic.getMachine();
        if (machine instanceof WorkableMultiblockMachine workableMultiblockMachine){
            for (IMultiPart part : workableMultiblockMachine.getParts()) {
                if (part instanceof BlazeVentMachine vent) {
                    if (vent.checkBlaze((long)(blazeConstant))){
                        return true;
                    }
                }
            }
        }
        recipeLogic.setWaiting(Component.literal("Not Enough Blaze!"));
        return false;
    }

    @Override
    public String getType() {
        return "blaze_vent";
    }

    @Override
    public Component getTooltips() {
        return null;
    }

    @Override
    public RecipeCondition createTemplate() {
        return null;
    }
}
