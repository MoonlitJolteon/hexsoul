package net.hexsoul.items;

import at.petrak.hexcasting.common.lib.HexSounds;
import net.hexsoul.Hexsoul;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class SoulOpener extends Item {
    public SoulOpener(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        player.playSound(HexSounds.READ_LORE_FRAGMENT, 1f, 1f);

        var handStack = player.getItemInHand(usedHand);
        if (!(player instanceof ServerPlayer splayer)) {
            handStack.shrink(1);
            return InteractionResultHolder.success(handStack);
        }
        var adv = splayer.server.getAdvancements().getAdvancement(new ResourceLocation(Hexsoul.MOD_ID, "opened_soul"));
        if(adv == null)
                return InteractionResultHolder.fail(handStack);
        if (!splayer.getAdvancements().getOrStartProgress(adv).isDone()) {
            splayer.getAdvancements().award(adv, "grant");
        }

        CriteriaTriggers.CONSUME_ITEM.trigger(splayer, handStack);
        splayer.awardStat(Stats.ITEM_USED.get(this));
        handStack.shrink(1);

        return InteractionResultHolder.success(handStack);
    }
}
