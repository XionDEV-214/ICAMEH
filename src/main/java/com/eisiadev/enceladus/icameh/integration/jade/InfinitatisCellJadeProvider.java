package com.eisiadev.enceladus.icameh.integration.jade;

import com.eisiadev.enceladus.icameh.machine.InfinitatisCellMachine;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class InfinitatisCellJadeProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(@NotNull ITooltip tooltip, @NotNull BlockAccessor accessor, @NotNull IPluginConfig config) {
        MetaMachine machine = MetaMachine.getMachine(accessor.getLevel(), accessor.getPosition());
        if (!(machine instanceof InfinitatisCellMachine cell)) return;

        long voltage = cell.getTierVoltage();
        int amperage = cell.getAmperage();
        long power = voltage * amperage;

        tooltip.add(Component.translatable("icameh.jade.infinitatis_cell.output",
                FormattingUtil.formatNumbers(voltage), amperage, FormattingUtil.formatNumbers(power)));

        ItemStack stack = cell.chargeSlot.getStackInSlot(0);
        if (!stack.isEmpty()) {
            IElectricItem electricItem = GTCapabilityHelper.getElectricItem(stack);
            if (electricItem != null) {
                tooltip.add(Component.translatable("icameh.jade.infinitatis_cell.charge",
                        FormattingUtil.formatNumbers(electricItem.getCharge()),
                        FormattingUtil.formatNumbers(electricItem.getMaxCharge())));
            }
        }
    }

    @Override
    public @Nullable ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath("icameh", "infinitatis_cell");
    }
}
