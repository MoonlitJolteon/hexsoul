package net.hexsoul.networking;

import at.petrak.hexcasting.common.msgs.MsgOpenSpellGuiS2C;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.hexsoul.Hexsoul;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import static net.hexsoul.Hexsoul.id;

public class HexsoulNetworking {
    private static final NetworkChannel CHANNEL = NetworkChannel.create(id("networking_channel"));
    public static final ResourceLocation OPEN_CASTING_GRID_PACKET_ID = new ResourceLocation(Hexsoul.MOD_ID, "open_casting_grid");

    public static void init() {
        CHANNEL.register(OpenGridC2SPacket.class, OpenGridC2SPacket::encode, OpenGridC2SPacket::new, OpenGridC2SPacket::apply);

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, OPEN_CASTING_GRID_PACKET_ID, ((buf, context) -> {
            Player player = context.getPlayer();
            if(!(player instanceof ServerPlayer serverPlayer)) return;
            boolean clear = buf.readBoolean();
            if(clear) IXplatAbstractions.INSTANCE.clearCastingData((ServerPlayer) player);

            if (!serverPlayer.serverLevel().isClientSide()) {
                var harness = IXplatAbstractions.INSTANCE.getStaffcastVM(serverPlayer, InteractionHand.MAIN_HAND);
                var patterns = IXplatAbstractions.INSTANCE.getPatternsSavedInUi(serverPlayer);
                var descs = harness.generateDescs();
                var advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(Hexsoul.MOD_ID, "opened_soul"));
                if(advancement == null)
                    return;
                if(!serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone())
                    return;

                IXplatAbstractions.INSTANCE.sendPacketToPlayer(serverPlayer,
                        new MsgOpenSpellGuiS2C(InteractionHand.MAIN_HAND, patterns, descs.getFirst(), descs.getSecond(),
                                0)); // TODO: Fix!
            }
        }));
    }

    public static <T> void sendToServer(T message) {
        CHANNEL.sendToServer(message);
    }

    public static void rawSendToServer(ResourceLocation message, FriendlyByteBuf buf) {
        NetworkManager.sendToServer(message, buf);
    }

    public static <T> void sendToPlayer(ServerPlayer player, T message) {
        CHANNEL.sendToPlayer(player, message);
    }

    public static <T> void sendToPlayers(Iterable<ServerPlayer> players, T message) {
        CHANNEL.sendToPlayers(players, message);
    }
}
