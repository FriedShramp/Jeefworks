package com.jeefle.jeefworks.api.renderer;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.client.renderer.machine.IControllerRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.MinerRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.jeefle.jeefworks.api.machine.multiblock.part.BlazeVentMachine;
import com.jeefle.jeefworks.api.machine.multiblock.part.NonConsumingHatchMachine;
import com.lowdragmc.lowdraglib.client.bakedpipeline.FaceQuad;
import com.lowdragmc.lowdraglib.client.model.ModelFactory;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.model.IDynamicBakedModel;

import javax.annotation.Nullable;
import java.util.List;

import static com.gregtechceu.gtceu.client.renderer.machine.LargeMinerRenderer.BEHIND_BLOCK;

public class BiovatRenderer extends WorkableCasingMachineRenderer {

    public BiovatRenderer(ResourceLocation baseCasing, ResourceLocation workableModel) {
        super(baseCasing, workableModel);
    }

    public static final AABB BEHIND_BLOCK = new AABB(0, -0.005, 1, 1, 1, 2);


    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderMachine(List<BakedQuad> quads, MachineDefinition definition, @Nullable MetaMachine machine, Direction frontFacing, @Nullable Direction side, RandomSource rand, @Nullable Direction modelFacing, ModelState modelState) {
        super.renderMachine(quads, definition, machine, frontFacing, side, rand, modelFacing, modelState);
        // We have to render it ourselves to avoid uv issues
        Fluid fluidType = null;

        if (machine instanceof WorkableMultiblockMachine workableMultiblockMachine) {
            for (IMultiPart p : workableMultiblockMachine.getParts()) {
                if (p instanceof BlazeVentMachine vent) {
                    if (vent.tank.getFluidInTank(0).getFluid() != null) {
                        fluidType = vent.tank.getFluidInTank(0).getRawFluid();
                    }
                }
            }
        }

        if (fluidType == null) return;

        ResourceLocation fluidSprite = IClientFluidTypeExtensions.of(fluidType).getStillTexture();

        if (true) {
            // pipe casing
            if (side != null && modelFacing != null) {
                if (side == Direction.UP) {
                    quads.add(FaceQuad.bakeFace(modelFacing, ModelFactory.getBlockSprite(fluidSprite), modelState));
                } else if (side == Direction.DOWN) {
                    quads.add(FaceQuad.bakeFace(modelFacing, ModelFactory.getBlockSprite(fluidSprite), modelState));
                } else {
                    quads.add(FaceQuad.bakeFace(modelFacing, ModelFactory.getBlockSprite(fluidSprite), modelState));
                }
            }
        } else {
            if (side != null && modelFacing != null) {
                quads.add(FaceQuad.bakeFace(modelFacing, ModelFactory.getBlockSprite(fluidSprite), modelState));
            }
        }


    }
}
