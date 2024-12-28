package net.hexsoul.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public class KeepGridOpen {
    @Inject(at = @At("HEAD"),
            method = "tick()V", cancellable = true)
    public void dontCloseWithRing(CallbackInfo callbackInfo){
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            // prob want to add a check here that the casting menu was opened by the player
            callbackInfo.cancel();
        }
    }
}
