package com.eisiadev.enceladus.icameh;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ICAMEHMod.MODID)
public class ICAMEHMod {

    public static final String MODID = "icameh";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final GTRegistrate REGISTRATE = GTRegistrate.create(MODID);

    public ICAMEHMod(FMLJavaModLoadingContext context) {
        LOGGER.info("[ICAMEH] Loading...");

        IEventBus modBus = context.getModEventBus();
        REGISTRATE.registerRegistrate();
        modBus.register(ICAMEHMachines.class);

        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onBuildCreativeTab);

        LOGGER.info("[ICAMEH] Loading complete.");
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("[ICAMEH] Common setup complete.");
    }

    private void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ResourceLocation gtMachineTabId = ResourceLocation.fromNamespaceAndPath("gtceu", "machine");
        if (!event.getTabKey().location().equals(gtMachineTabId)) return;

        for (var def : ICAMEHMachines.INFINITATIS_CELLS) {
            if (def != null) event.accept(def.getItem());
        }
        for (var def : ICAMEHMachines.MORE_ENERGY_HATCHES) {
            if (def != null) event.accept(def.getItem());
        }
    }
}
