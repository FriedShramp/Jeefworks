package com.jeefle.jeefworks.api.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

public class NonConsumingHatchMachine extends TieredPartMachine implements IFancyUIMachine {
    protected final IO io;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NonConsumingHatchMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    public final NotifiableFluidTank tank;

    @Persisted
    public final NotifiableItemStackHandler inv;

    public NonConsumingHatchMachine(IMachineBlockEntity holder, int tier, int tankcapacity) {
        super(holder, tier);
        this.io = IO.BOTH;
        this.tank = createTank(tankcapacity);
        this.inv = createInv();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected NotifiableFluidTank createTank(int capacity, Object... args) {
        return new NotifiableFluidTank(this, 1, capacity, IO.BOTH);
    }

    protected NotifiableItemStackHandler createInv(Object... args) {
        return new NotifiableItemStackHandler(this, 1, IO.BOTH);
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 89, 63);

        group.addWidget(new ImageWidget(4, 4, 81, 55, GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(8, 8, "Substrate:"))
                .addWidget(new TankWidget(tank.storages[0], 50, 22, true, IO.IN.support(IO.IN)).setBackground(GuiTextures.FLUID_SLOT))
                .addWidget(new SlotWidget(inv, 0, 19, 22));

        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }
}
