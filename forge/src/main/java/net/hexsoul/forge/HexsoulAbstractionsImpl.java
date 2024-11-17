package net.hexsoul.forge;

import net.hexsoul.HexsoulAbstractions;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HexsoulAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexsoulAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
	
    public static void initPlatformSpecific() {
        HexsoulConfigForge.init();
    }
}
