package com.eisiadev.enceladus.icameh.machine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;

public class MoreEnergyHatchPartMachine extends EnergyHatchPartMachine {

    public MoreEnergyHatchPartMachine(IMachineBlockEntity info, int tier, IO io, int amperage) {
        super(info, tier, io, amperage);
    }
}
