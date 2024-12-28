package net.hexsoul.networking;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class OpenGridC2SPacket {
    boolean clearFlag;
    public OpenGridC2SPacket(boolean clearFlag) {
        this.clearFlag = clearFlag;
    }
    public OpenGridC2SPacket(FriendlyByteBuf buf) {
        this.clearFlag = buf.readBoolean();
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(clearFlag);
    }
    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        System.out.println("Hello from Apply");
    }
}
