package net.hexsoul.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.hexsoul.HexsoulAbstractions;

import java.nio.file.Path;

public class HexsoulAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexsoulAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
	
    public static void initPlatformSpecific() {
        HexsoulConfigFabric.init();
    }
}
