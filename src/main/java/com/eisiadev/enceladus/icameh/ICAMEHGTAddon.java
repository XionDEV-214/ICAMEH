package com.eisiadev.enceladus.icameh;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

@GTAddon
public class ICAMEHGTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return ICAMEHMod.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        ICAMEHMod.LOGGER.info("[ICAMEH] Addon initialized.");
    }

    @Override
    public String addonModId() {
        return ICAMEHMod.MODID;
    }
}
