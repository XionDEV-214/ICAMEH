package com.eisiadev.enceladus.icameh.machine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InfinitatisCellMachine extends TieredEnergyMachine implements IFancyUIMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(InfinitatisCellMachine.class, TieredEnergyMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    public final NotifiableItemStackHandler chargeSlot;

    private final int amperage;
    private final long voltage;

    public InfinitatisCellMachine(IMachineBlockEntity info, int tier, int amperage) {
        super(info, tier);
        this.amperage = amperage;
        this.voltage = GTValues.V[tier];
        long capacity = voltage * amperage * 2L;
        this.energyContainer.resetBasicInfo(capacity, 0L, 0L, voltage, amperage);
        this.energyContainer.setSideOutputCondition(s -> s == getFrontFacing());
        this.energyContainer.setCapabilityValidator(s -> s == null || s == getFrontFacing());
        this.chargeSlot = new NotifiableItemStackHandler(this, 1, IO.IN);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!isRemote()) {
            subscribeServerTick(this::tick);
        }
    }

    private void tick() {
        this.energyContainer.setEnergyStored(voltage * amperage * 2L);

        ItemStack stack = chargeSlot.getStackInSlot(0);
        if (!stack.isEmpty()) {
            IElectricItem electricItem = GTCapabilityHelper.getElectricItem(stack);
            if (electricItem != null && electricItem.chargeable()) {
                long toCharge = voltage * amperage;
                electricItem.charge(toCharge, tier, false, false);
            }
        }
    }

    public int getAmperage() {
        return amperage;
    }

    public long getTierVoltage() {
        return voltage;
    }

    @Override
    public com.lowdragmc.lowdraglib.gui.widget.Widget createUIWidget() {
        WidgetGroup group = new WidgetGroup(0, 0, 176, 60);
        group.addWidget(new LabelWidget(5, 5, getDefinition().getDescriptionId()));
        group.addWidget(new SlotWidget(chargeSlot, 0, 80, 25, true, true)
                .setBackground(GuiTextures.SLOT));
        return group;
    }
}
