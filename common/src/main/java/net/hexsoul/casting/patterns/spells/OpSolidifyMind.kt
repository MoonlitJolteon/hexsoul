package net.hexsoul.casting.patterns.spells

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.HexItems
import net.hexsoul.casting.mishaps.MishapNoCastingEntity
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.phys.Vec3
import kotlin.math.max

class OpSolidifyMind : SpellAction {
    /**
     * The number of arguments from the stack that this action requires.
     */
    override val argc = 1
    val cost = 2 * MediaConstants.DUST_UNIT

    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns a triple of things. The [RenderedSpell] is responsible for the spell actually
     * doing things in the world, the [Int] is how much media the spell should cost,
     * and the [List] of [ParticleSpray] renders particle effects for the result of the SpellAction.
     *
     * The [execute] method should only contain code to find the targets of the spell and validate
     * them. All the code that actually makes changes to the world (breaking blocks, teleporting things,
     * etc.) should be in the private [Spell] data class below.
     */
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val iota = args[0] as DoubleIota

        if (env.castingEntity == null) throw MishapNoCastingEntity(
            Component.translatable("text.hexsoul.casting_entity")
        );

        return SpellAction.Result(
            Spell(iota.double),
            (cost * iota.double).toLong(),
            listOf(ParticleSpray(env.castingEntity!!.position().add(0.0, 2.0, 0.0), Vec3(0.0, -1.0, 0.0), 0.5, 0.1))
        )
    }

    /**
     * This class is responsible for actually making changes to the world. It accepts parameters to
     * define where/what it should affect (for this example the parameter is [player]), and the
     * [cast] method within is responsible for using that data to alter the world.
     */
    private data class Spell(val number: Double) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val test = HexItems.AMETHYST_DUST.defaultInstance;
            test.count = max(number, 1.0).toInt();
            val ent = env.castingEntity!!;
            val entity = ItemEntity(env.world, ent.x, ent.y, ent.z, test);
            env.world.addFreshEntity(entity);
        }
    }
}
