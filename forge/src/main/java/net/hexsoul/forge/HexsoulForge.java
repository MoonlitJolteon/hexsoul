package net.hexsoul.forge;

import at.petrak.hexcasting.forge.recipe.ForgeModConditionalIngredient;
import at.petrak.hexcasting.forge.recipe.ForgeUnsealedIngredient;
import dev.architectury.platform.forge.EventBuses;
import net.hexsoul.Hexsoul;
import net.hexsoul.registry.HexsoulItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
        bind(Registries.ITEM, HexsoulItems::registerItems);
        initListeners();
        Hexsoul.init();
    }

    private static void initListeners() {
        var modBus = getModEventBus();
        var evBus = MinecraftForge.EVENT_BUS;

        modBus.addListener((BuildCreativeModeTabContentsEvent evt) -> {
            HexsoulItems.registerItemCreativeTab(evt, evt.getTab());
        });
    }

    private static <T> void bind(ResourceKey<? extends Registry<T>> registry,
                                 Consumer<BiConsumer<T, ResourceLocation>> source) {
        System.out.println("Binding.....");
        getModEventBus().addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> {
                    event.register(registry, rl, () -> t);
                    System.out.println((t.toString() + "  " + rl.toString()));
                });
            }
        });
    }

    private static IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }
}
