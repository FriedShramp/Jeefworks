package com.jeefle.jeefworks.api.machine.multiblock.part;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IWorkableMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeCapabilities;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.data.pack.GTDynamicDataPack;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import com.lowdragmc.lowdraglib.syncdata.ISubscription;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


//mostly stolen from gt--
public class BlazeVentMachine extends TieredPartMachine implements IFancyUIMachine {

    private double discount = 0.6;
    private double quicken = 0.3;
    private double blazeConstant = 2.2;
    private double bcount = 0;

    private static final Fluid[] validFluids = {GTMaterials.Blaze.getFluid(), Fluids.LAVA};
    protected final IO io;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(BlazeVentMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    public final NotifiableFluidTank tank;

    public final long tankCapacity;

    public BlazeVentMachine(IMachineBlockEntity holder, int tier, long initialCapacity, Object... args) {
        super(holder, tier);
        this.tankCapacity = initialCapacity;
        this.tank = createTank();
        this.io = IO.IN;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }


    @Override
    public void onWorking(IWorkableMultiController controller) {
        super.beforeWorking(controller);
        this.bcount += blazeConstant;
        if (bcount >= 1){
            this.consumeBlaze((long) Math.floor(bcount));
            bcount = bcount - Math.floor(bcount);
        }
    }

    protected NotifiableFluidTank createTank(Object... args) {
        return new NotifiableFluidTank(this, 1, tankCapacity, IO.BOTH);
    }

    public long consumeBlaze(long fluidAmount){
        if (this.tank.drain(fluidAmount,true).getAmount() == fluidAmount){
            return Math.abs(this.tank.drain(fluidAmount, false).getAmount());
        } else{
            return 0;
        }
    }

    public boolean getValidContents(GTRecipe recipe){
        boolean valid = Stream.of(validFluids).anyMatch(x -> x == this.tank.getFluidInTank(0).getFluid());
        if (valid && this.tank.getFluidInTank(0).getAmount() >= Math.floor(this.blazeConstant) && recipe.getType() == GTRecipeTypes.BLAST_RECIPES){
            if (recipe.data.getInt("vent_temp") <= getTemp()){
                return true;
            }else{
                recipe.data.putInt("ebf_temp", 100000);
                return false;
            }
        }
        return false;
    }

    public int getTemp(){
        boolean valid = Stream.of(validFluids).anyMatch(x -> x == this.tank.getFluidInTank(0).getFluid());
        if (valid){
            if (this.tank.getFluidInTank(0).getAmount() < blazeConstant){
                return 0;
            }
            return tank.getFluidInTank(0).getFluid().getFluidType().getTemperature();
        }
        return 0;
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 89, 63);

        group.addWidget(new ImageWidget(4, 4, 81, 55, GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(8, 8, "gtceu.gui.fluid_amount"))
                .addWidget(new LabelWidget(8, 18, () -> String.valueOf(tank.getFluidInTank(0).getAmount())).setTextColor(-1).setDropShadow(true))
                .addWidget(new TankWidget(tank.storages[0], 44, 22, true, IO.IN.support(IO.IN)).setBackground(GuiTextures.FLUID_SLOT));

        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    public double getDiscount(){
        if (this.tank.getFluidInTank(0).getAmount() != 0){
            this.discount = (double) 2400 / this.tank.getFluidInTank(0).getFluid().getFluidType().getTemperature();
        }
        return this.discount;
    }

    public double getQuicken(){
        if (this.tank.getFluidInTank(0).getAmount() != 0){
            this.quicken = (double) 1400 / this.tank.getFluidInTank(0).getFluid().getFluidType().getTemperature();
        }
        return this.quicken;
    }

    @Override
    public GTRecipe modifyRecipe(GTRecipe recipe){
        GTRecipe newRecipe = recipe.copy();
        newRecipe.data.putInt("vent_temp", recipe.data.getInt("ebf_temp"));
        newRecipe.data.putInt("ebf_temp", 0);

        long newEu = (long) Math.floor((double) RecipeHelper.getInputEUt(newRecipe) * getDiscount());

        for (Content content : newRecipe.getTickInputContents(EURecipeCapability.CAP)) {
            content.content = newEu;
        }
        newRecipe.duration = (int) Math.floor(newRecipe.duration * getQuicken());
        return newRecipe;
    }
}
