package com.eisiadev.enceladus.icameh;

import com.eisiadev.enceladus.icameh.machine.InfinitatisCellMachine;
import com.eisiadev.enceladus.icameh.machine.MoreEnergyHatchPartMachine;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.gregtechceu.gtceu.api.GTValues.*;

public class ICAMEHMachines {

    private static boolean initialized = false;

    public static final List<MachineDefinition> INFINITATIS_CELLS = new ArrayList<>();
    public static final List<MachineDefinition> MORE_ENERGY_HATCHES = new ArrayList<>();

    public static final int[] INFINITATIS_AMPS = { 1, 2, 4, 16, 64, 256, 1024, 4096 };
    public static final int[] MORE_HATCH_AMPS = { 64, 256, 1024, 4096 };
    public static final int[] MORE_HATCH_TIERS = tiersBetween(EV, OpV);

    @SubscribeEvent
    public static void registerMachines(GTCEuAPI.RegisterEvent event) {
        if (initialized || event.getGenericType() != MachineDefinition.class) return;
        init();
        initialized = true;
    }

    public static void init() {
        registerInfinitatisCells();
        registerMoreEnergyHatches();
    }

    private static void registerInfinitatisCells() {
        for (int tier = LV; tier <= OpV; tier++) {
            final int finalTier = tier;
            for (int amperage : INFINITATIS_AMPS) {
                final int finalAmperage = amperage;
                String name = VN[finalTier].toLowerCase(Locale.ROOT) + "_infinitatis_cell_" + finalAmperage + "a";

                var definition = ICAMEHMod.REGISTRATE
                        .machine(name, info -> new InfinitatisCellMachine(info, finalTier, finalAmperage))
                        .tier(finalTier)
                        .rotationState(RotationState.ALL)
                        .langValue(VCF[finalTier] + VN[finalTier] + " Infinitatis Cell " + String.format("%,d", finalAmperage) + "A" + "§r")
                        .modelProperty(GTMachineModelProperties.IS_FORMED, false)
                        .model((ctx, prov, builder) -> {})
                        .tooltips(
                                Component.translatable("icameh.machine.infinitatis_cell.tooltip"),
                                Component.translatable("gtceu.universal.tooltip.voltage_out",
                                        FormattingUtil.formatNumbers(V[finalTier]), VNF[finalTier]),
                                Component.translatable("gtceu.universal.tooltip.amperage_out", finalAmperage),
                                Component.literal("§7Max Output: §r" + FormattingUtil.formatNumbers(V[finalTier] * finalAmperage) + " §7EU/t§r")
                        )
                        .register();
                INFINITATIS_CELLS.add(definition);
            }
        }
    }

    private static void registerMoreEnergyHatches() {
        for (int tier : MORE_HATCH_TIERS) {
            final int finalTier = tier;
            for (int amperage : MORE_HATCH_AMPS) {
                final int finalAmperage = amperage;
                String name = VN[finalTier].toLowerCase(Locale.ROOT) + "_energy_input_hatch_" + finalAmperage + "a";

                var definition = ICAMEHMod.REGISTRATE
                        .machine(name, info -> new MoreEnergyHatchPartMachine(info, finalTier, IO.IN, finalAmperage))
                        .tier(finalTier)
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.INPUT_ENERGY)
                        .langValue(VCF[finalTier] + VN[finalTier] + " " + String.format("%,d", finalAmperage) + "A Energy Hatch" + "§r")
                        .modelProperty(GTMachineModelProperties.IS_FORMED, false)
                        .model((ctx, prov, builder) -> {})
                        .tooltips(
                                Component.translatable("gtceu.universal.tooltip.voltage_in",
                                        FormattingUtil.formatNumbers(V[finalTier]), VNF[finalTier]),
                                Component.translatable("gtceu.universal.tooltip.amperage_in", finalAmperage),
                                Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                        FormattingUtil.formatNumbers(EnergyHatchPartMachine.getHatchEnergyCapacity(finalTier, finalAmperage))),
                                Component.translatable("gtceu.machine.energy_hatch.input.tooltip")
                        )
                        .register();
                MORE_ENERGY_HATCHES.add(definition);
            }
        }
    }

}
