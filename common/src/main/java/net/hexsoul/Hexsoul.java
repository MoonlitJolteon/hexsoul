package net.hexsoul;

import net.hexsoul.registry.HexsoulIotaTypeRegistry;
import net.hexsoul.registry.HexsoulItemRegistry;
import net.hexsoul.registry.HexsoulPatternRegistry;
import net.hexsoul.networking.HexsoulNetworking;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class Hexsoul {
    public static final String MOD_ID = "hexsoul";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("Hex Soul says hello!");

        HexsoulAbstractions.initPlatformSpecific();
        HexsoulItemRegistry.init();
        HexsoulIotaTypeRegistry.init();
        HexsoulPatternRegistry.init();
		HexsoulNetworking.init();

        LOGGER.info(HexsoulAbstractions.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }
}
