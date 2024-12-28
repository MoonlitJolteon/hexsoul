package net.hexsoul.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import io.netty.buffer.Unpooled;
import net.hexsoul.Hexsoul;
import net.hexsoul.networking.HexsoulNetworking;
import net.hexsoul.networking.OpenGridC2SPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public final class HexSoulKeybindings {
    public static final HexSoulKeybindings INSTANCE = new HexSoulKeybindings();
    private HexSoulKeybindings() {}

    public final KeyMapping open_grid = new KeyMapping("key." + Hexsoul.MOD_ID + ".open_casting_grid", InputConstants.KEY_G, "key.categories." + Hexsoul.MOD_ID);
    public void registerKeybinds() {
        KeyMappingRegistry.register(open_grid);
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (HexSoulKeybindings.INSTANCE.open_grid.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                assert player != null;
                buf.writeBoolean(player.isCrouching());
                HexsoulNetworking.rawSendToServer(HexsoulNetworking.OPEN_CASTING_GRID_PACKET_ID, buf);
            }
        });
    }
}
