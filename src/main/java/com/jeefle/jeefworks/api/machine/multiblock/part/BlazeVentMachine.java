package com.jeefle.jeefworks.api.machine.multiblock.part;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IWorkableMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import com.lowdragmc.lowdraglib.syncdata.ISubscription;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;


//mostly stolen from gt--
public class BlazeVentMachine extends TieredPartMachine implements IFancyUIMachine {

    private static final double discount = 0.7;
    private static final double quicken = 0.5;

    private static final double consumptionRate = 0.5;

    @Persisted
    public final NotifiableFluidTank tank;

    public final long tankCapacity;

    public BlazeVentMachine(IMachineBlockEntity holder, int tier, long initialCapacity, Object... args) {
        super(holder, tier);
        this.tankCapacity = initialCapacity;
        this.tank = createTank();
    }

    @Override
    public void beforeWorking(IWorkableMultiController controller) {
        super.beforeWorking(controller);
        this.consumeBlaze(controller.getRecipeLogic().getDuration());
    }

    protected NotifiableFluidTank createTank(Object... args) {
        return new NotifiableFluidTank(this, 1, tankCapacity, IO.BOTH);
    }

    public long consumeBlaze(long fluidAmount){
        if (this.tank.drain(fluidAmount,true).getAmount() == fluidAmount && this.tank.getFluidInTank(0).getRawFluid() == Fluids.WATER){
            return Math.abs(this.tank.drain(fluidAmount, false).getAmount());
        } else{
            return 0;
        }
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 89, 63);

        group.addWidget(new ImageWidget(4, 4, 81, 55, GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(8, 8, "gtceu.gui.fluid_amount"))
                .addWidget(new LabelWidget(8, 18, () -> String.valueOf(tank.getFluidInTank(0).getAmount())).setTextColor(-1).setDropShadow(true))
                .addWidget(new TankWidget(tank.storages[0], 67, 22, true, IO.IN.support(IO.IN)).setBackground(GuiTextures.FLUID_SLOT));

        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    @Override
    public GTRecipe modifyRecipe(GTRecipe recipe){
        GTRecipe newRecipe = recipe.copy();
        if (true) {
            if (true) {
                long newEu = (long) Math.floor((double) RecipeHelper.getInputEUt(newRecipe) * discount);
                for (Content content : newRecipe.getTickInputContents(EURecipeCapability.CAP)) {
                    content.content = newEu;
                }
                newRecipe.duration = (int) Math.floor(newRecipe.duration * quicken);
                this.consumeBlaze(1);
            }
        }
        return newRecipe;
    }

    public GTRecipe doBlaze(GTRecipe recipe){
        if (consumeBlaze((long) Math.floor(recipe.duration * consumptionRate)) > 0){
            return recipe;
        }else {
            return null;
        }
    }
}
