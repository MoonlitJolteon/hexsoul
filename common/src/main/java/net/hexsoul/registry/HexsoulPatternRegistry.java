package net.hexsoul.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.hexsoul.casting.patterns.spells.OpSolidifyMind;

import static net.hexsoul.Hexsoul.MOD_ID;

public class HexsoulPatternRegistry {
    public static DeferredRegister<ActionRegistryEntry> PATTERN_REGISTY = DeferredRegister.create(MOD_ID, HexRegistries.ACTION);
    // IMPORTANT: be careful to keep the registration calls looking like this, or you'll have to edit the hexdoc pattern regex.
        public static RegistrySupplier<ActionRegistryEntry> CONGRATS = register(HexPattern.fromAngles("dewwqaqwwed", HexDir.NORTH_EAST), "solidify_mind", new OpSolidifyMind());

    public static void init() {
        PATTERN_REGISTY.register();
    }

    private static RegistrySupplier<ActionRegistryEntry> register(HexPattern pattern, String name, Action action) {
        return PATTERN_REGISTY.register(name, () -> new ActionRegistryEntry(pattern, action));
    }

    // TODO: Figure out how per world registries work in 1.20
    private static RegistrySupplier<ActionRegistryEntry> registerPerWorld( HexPattern pattern,String name, Action action) {
        return PATTERN_REGISTY.register(name, () -> new ActionRegistryEntry(pattern, action));
    }
}
