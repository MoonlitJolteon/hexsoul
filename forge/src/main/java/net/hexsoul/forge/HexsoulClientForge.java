package net.hexsoul.forge;

import net.hexsoul.HexsoulClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class HexsoulClientForge {
    public static void init(FMLClientSetupEvent event) {
        HexsoulClient.init();
    }
}
