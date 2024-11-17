package net.hexsoul.forge;

import dev.architectury.platform.forge.EventBuses;
import net.hexsoul.Hexsoul;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(Hexsoul.MOD_ID)
public class HexsoulForge {
    public HexsoulForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Hexsoul.MOD_ID, bus);
        bus.addListener(HexsoulClientForge::init);
        Hexsoul.init();
    }
}
