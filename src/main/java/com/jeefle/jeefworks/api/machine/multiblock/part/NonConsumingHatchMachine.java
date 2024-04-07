package com.jeefle.jeefworks.api.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

public class NonConsumingHatchMachine extends TieredPartMachine implements IFancyUIMachine {
    protected final IO io;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NonConsumingHatchMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    public final NotifiableFluidTank tank;

    @Persisted
    public final NotifiableItemStackHandler inv;

    public NonConsumingHatchMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        this.io = IO.BOTH;
        this.tank = createTank();
        this.inv = createInv();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected NotifiableFluidTank createTank(Object... args) {
        return new NotifiableFluidTank(this, 1, 1000, IO.BOTH);
    }

    protected NotifiableItemStackHandler createInv(Object... args) {
        return new NotifiableItemStackHandler(this, 1, IO.BOTH);
    }
}
