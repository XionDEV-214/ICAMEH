package com.eisiadev.enceladus.icameh.integration.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ICAMEHJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new InfinitatisCellJadeProvider(), net.minecraft.world.level.block.Block.class);
    }
}
