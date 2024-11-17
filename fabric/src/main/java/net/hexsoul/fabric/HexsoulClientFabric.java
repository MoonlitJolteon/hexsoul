package net.hexsoul.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.hexsoul.HexsoulClient;

/**
 * Fabric client loading entrypoint.
 */
public class HexsoulClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexsoulClient.init();
    }
}
